package api.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author Tim
 */
public class GrandExchange {
	private static final String HOST = "http://services.runescape.com";
	private static final String GET = "/m=itemdb_rs/viewitem.ws?obj=";
	private static final String WIKIHOST = "http://runescape.wikia.com/wiki/";
	private static final String WIKIBASE = "Exchange:";

	public static final int INTERFACE_GRAND_EXCHANGE = 105;
	public static final int INTERFACE_BUY_SEARCH_BOX = 389;
	public static final int[] INTERFACE_GRAND_EXCHANGE_BUY_BUTTON = {31, 47, 63, 82, 101, 120};
	public static final int[] INTERFACE_GRAND_EXCHANGE_SELL_BUTTON = {32, 48, 64, 83, 102, 121};
	public static final int[] INTERFACE_GRAND_EXCHANGE_OFFER_BOXES = {19, 35, 51, 67, 83, 99};

	public static final int GRAND_EXCHANGE_COLLECT_BOX_ONE = 206;
	public static final int GRAND_EXCHANGE_COLLECT_BOX_TWO = 208;

	public static final int[] GRAND_EXCHANGE_CLERK = {6528, 6529,
			1419, 2240, 2241, 2593};


	/**
	 * Gets the name of the given item ID. Should not be used.
	 *
	 * @param itemID The item ID to look for.
	 * @return The name of the given item ID or an empty String if unavailable.
	 * @see GrandExchange#lookup(int)
	 */
	public static String getItemName(final int itemID) {
		final GEItem geItem = lookup(itemID);
		if (geItem != null) {
			return GEItem.getName();
		}
		return "";
	}

	/**
	 * Gets the ID of the given item name. Should not be used.
	 *
	 * @param itemName The name of the item to look for.
	 * @return The ID of the given item name or -1 if unavailable.
	 * @see GrandExchange#lookup(java.lang.String)
	 */
	public static int getItemID(final String itemName) {
		final GEItem geItem = lookup(itemName);
		if (geItem != null) {
			return GEItem.getID();
		}
		return -1;
	}

	/**
	 * Collects data for a given item ID from the Grand Exchange website.
	 *
	 * @param itemID The item ID.
	 * @return An instance of GrandExchange.GEItem; <code>null</code> if unable
	 *         to fetch data.
	 */
	public static GEItem lookup(final int itemID) {
		try {
			final URL url = new URL(GrandExchange.HOST + GrandExchange.GET + itemID);
			final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String input;
			boolean exists = false;
			int i = 0;
			final double[] values = new double[4];
			String name = "", examine = "";
			while ((input = br.readLine()) != null) {
				if (input.contains("<div class=\"brown_box main_ge_page") && !exists) {
					if (!input.contains("vertically_spaced")) {
						return null;
					}
					exists = true;
					br.readLine();
					br.readLine();
					name = br.readLine();
				} else if (input.contains("<img id=\"item_image\" src=\"")) {
					examine = br.readLine();
				} else if (input.matches("(?i).+ (price|days):</b> .+")) {
					values[i] = parse(input);
					i++;
				} else if (input.matches("<div id=\"legend\">")) {
					break;
				}
			}
			return new GEItem(name, examine, itemID, values);
		} catch (final IOException ignore) {
		}
		return null;
	}

	/**
	 * Collects data for a given item name from the Grand Exchange website.
	 *
	 * @param itemName The name of the item.
	 * @return An instance of GrandExchange.GEItem; <code>null</code> if unable
	 *         to fetch data.
	 */
	public static GEItem lookup(String itemName) {
		try {
			final URL url = new URL(WIKIHOST + WIKIBASE +makeNameEdits(itemName));
			final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String input;
			while ((input = br.readLine()) != null) {
				if (input.contains("Lookup current price")) {
					String[] inputs = input.split("\"");
					int itemID = Integer.parseInt(inputs[1].split("obj=")[1]);
					return lookup(itemID);
				}
			}
		} catch (final IOException ignored) {
		}
		return null;
	}

	private static String makeNameEdits(String itemName) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String currString: itemName.trim().split(" ")) {
			if (first || currString.length() < 1) {
				sb.append(currString);
				first = false;
				continue;
			}
			sb.append('_');
            sb.append(currString.substring(0, 1).toLowerCase()).append(currString.substring(1));
		}
		return sb.toString();
	}

	private static double parse(String str) {
		if (str != null && !str.isEmpty()) {
			str = stripFormatting(str);
			str = str.substring(str.indexOf(58) + 2, str.length());
			str = str.replace(",", "");
			str = str.trim();
			if (!str.endsWith("%")) {
				if (!str.endsWith("k") && !str.endsWith("m") && !str.endsWith("b")) {
					return Double.parseDouble(str);
				}
				return Double.parseDouble(str.substring(0, str.length() - 1)) * (str.endsWith("b") ? 1000000000 : str.endsWith("m") ? 1000000 : 1000);
			}
			final int k = str.startsWith("+") ? 1 : -1;
			str = str.substring(1);
			return Double.parseDouble(str.substring(0, str.length() - 1)) * k;
		}
		return -1D;
	}

	private static String stripFormatting(final String str) {
		if (str != null && !str.isEmpty()) {
			return str.replaceAll("(^[^<]+>|<[^>]+>|<[^>]+$)", "");
		}
		return "";
	}

	/**
	 * Provides access to GEItem Information.
	 */
	public static class GEItem {
		private static String name;
		private static String examine;

		private static int id;

		private static int guidePrice;

		private static double change30;
		private static double change90;
		private static double change180;

		GEItem(final String name, final String examine, final int id, final double[] values) {
			GEItem.name = name;
			GEItem.examine = examine;
			GEItem.id = id;
			guidePrice = (int) values[0];
			change30 = values[1];
			change90 = values[2];
			change180 = values[3];
		}

		/**
		 * Gets the change in price for the last 30 days of this item.
		 *
		 * @return The change in price for the last 30 days of this item.
		 */
		public static double getChange30Days() {
			return change30;
		}

		/**
		 * Gets the change in price for the last 90 days of this item.
		 *
		 * @return The change in price for the last 90 days of this item.
		 */
		public static double getChange90Days() {
			return change90;
		}

		/**
		 * Gets the change in price for the last 180 days of this item.
		 *
		 * @return The change in price for the last 180 days of this item.
		 */
		public static double getChange180Days() {
			return change180;
		}

		/**
		 * Gets the ID of this item.
		 *
		 * @return The ID of this item.
		 */
		public static int getID() {
			return id;
		}

		/**
		 * Gets the market price of this item.
		 *
		 * @return The market price of this item.
		 */
		public static int getGuidePrice() {
			return guidePrice;
		}

		/**
		 * Gets the name of this item.
		 *
		 * @return The name of this item.
		 */
		public static String getName() {
			return name;
		}

		/**
		 * Gets the description of this item.
		 *
		 * @return The description of this item.
		 */
		public static String getDescription() {
			return examine;
		}
	}
}