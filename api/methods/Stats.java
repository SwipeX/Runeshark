package api.methods;

import java.awt.*;

/**
 * @Author Dwarfeh
 */
public class Stats {
    //row 1
    public static final Rectangle ATTACK_RECTANGLE = new Rectangle(549, 211, 58, 24);
    public static final Rectangle STRENGTH_RECTANGLE = new Rectangle(549, 239, 58, 24);
    public static final Rectangle DEFENSE_RECTANGLE = new Rectangle(549, 267, 58, 24);
    public static final Rectangle RANGE_RECTANGLE = new Rectangle(549, 295, 58, 24);
    public static final Rectangle PRAYER_RECTANGLE = new Rectangle(549, 323, 58, 24);
    public static final Rectangle MAGIC_RECTANGLE = new Rectangle(549, 351, 58, 24);
    /*  Not Used:
    public static final Rectangle RUNECRAFTING_RECTANGLE = new Rectangle(549, 379, 58, 24);
    public static final Rectangle CONSTRUCTION_RECTANGLE = new Rectangle(549, 407, 58, 24);
    public static final Rectangle DUNGEONEERING_RECTANGLE = new Rectangle(549, 435, 58, 24);
     */
    //row 2
    public static final Rectangle CONSTITUTION_RECTANGLE = new Rectangle(611, 211, 58, 24);
    public static final Rectangle AGILITY_RECTANGLE = new Rectangle(611, 239, 58, 24);
    public static final Rectangle HERBLORE_RECTANGLE = new Rectangle(611, 267, 58, 24);
    public static final Rectangle THIEVING_RECTANGLE = new Rectangle(611, 295, 58, 24);
    public static final Rectangle CRAFTING_RECTANGLE = new Rectangle(611, 323, 58, 24);
    public static final Rectangle FLETCHING_RECTANGLE = new Rectangle(611, 351, 58, 24);
    public static final Rectangle SLAYER_RECTANGLE = new Rectangle(611, 379, 58, 24);
    /*  Not Used:
    public static final Rectangle HUNTING_RECTANGLE = new Rectangle(549, 407, 58, 24);
    */
    //row 3
    public static final Rectangle MINING_RECTANGLE = new Rectangle(673, 211, 58, 24);
    public static final Rectangle SMITHING_RECTANGLE = new Rectangle(673, 239, 58, 24);
    public static final Rectangle FISHING_RECTANGLE = new Rectangle(673, 267, 58, 24);
    public static final Rectangle COOKING_RECTANGLE = new Rectangle(673, 295, 58, 24);
    public static final Rectangle FIREMAKING_RECTANGLE = new Rectangle(673, 323, 58, 24);
    public static final Rectangle WOODCUTTING_RECTANGLE = new Rectangle(673, 351, 58, 24);
    public static final Rectangle FARMING_RECTANGLE = new Rectangle(673, 379, 58, 24);
    /* Not Used:
    public static final Rectangle SUMMONING_RECTANGLE = new Rectangle(673, 407, 58, 24);
     */
    //TODO Look for rest of stat text that is above rather than below. The "not used"


    //TODO eventually rewrite it all so casting as int will be redundant. I derped on that
    public static enum stats {
        //column 1
        ATTACK(ATTACK_RECTANGLE,
                new Rectangle((int)ATTACK_RECTANGLE.getCenterX() +11, (int)ATTACK_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle((int)ATTACK_RECTANGLE.getCenterX() +11, (int)ATTACK_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle((int)ATTACK_RECTANGLE.getCenterX() +11, (int)ATTACK_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle((int)ATTACK_RECTANGLE.getCenterX() +11, (int)ATTACK_RECTANGLE.getCenterY()+75, 150, 23)),

        STRENGTH(STRENGTH_RECTANGLE,
                new Rectangle((int)STRENGTH_RECTANGLE.getCenterX() +11, (int)STRENGTH_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle((int)STRENGTH_RECTANGLE.getCenterX() +11, (int)STRENGTH_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle((int)STRENGTH_RECTANGLE.getCenterX() +11, (int)STRENGTH_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle((int)STRENGTH_RECTANGLE.getCenterX() +11, (int)STRENGTH_RECTANGLE.getCenterY()+75, 150, 23)),

        DEFENSE(DEFENSE_RECTANGLE,
                new Rectangle((int)DEFENSE_RECTANGLE.getCenterX() + 11, (int)DEFENSE_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle((int)DEFENSE_RECTANGLE.getCenterX() + 11, (int)DEFENSE_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle((int)DEFENSE_RECTANGLE.getCenterX() + 11, (int)DEFENSE_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle((int)DEFENSE_RECTANGLE.getCenterX() + 11, (int)DEFENSE_RECTANGLE.getCenterY()+75, 150, 23)),

        RANGE(RANGE_RECTANGLE,
                new Rectangle((int)RANGE_RECTANGLE.getCenterX() + 11, (int)RANGE_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle((int)RANGE_RECTANGLE.getCenterX() + 11, (int)RANGE_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle((int)RANGE_RECTANGLE.getCenterX() + 11, (int)RANGE_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle((int)RANGE_RECTANGLE.getCenterX() + 11, (int)RANGE_RECTANGLE.getCenterY()+75, 150, 23)),

        PRAYER(PRAYER_RECTANGLE,
                new Rectangle((int)PRAYER_RECTANGLE.getCenterX() + 11, (int)PRAYER_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle((int)PRAYER_RECTANGLE.getCenterX() + 11, (int)PRAYER_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle((int)PRAYER_RECTANGLE.getCenterX() + 11, (int)PRAYER_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle((int)PRAYER_RECTANGLE.getCenterX() + 11, (int)PRAYER_RECTANGLE.getCenterY()+75, 150, 23)),

        MAGIC(MAGIC_RECTANGLE,
                new Rectangle((int)MAGIC_RECTANGLE.getCenterX() + 11, (int)MAGIC_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle((int)MAGIC_RECTANGLE.getCenterX() + 11, (int)MAGIC_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle((int)MAGIC_RECTANGLE.getCenterX() + 11, (int)MAGIC_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle((int)MAGIC_RECTANGLE.getCenterX() + 11, (int)MAGIC_RECTANGLE.getCenterY()+75, 150, 23)),

        //column 2
        CONSTITUTION(CONSTITUTION_RECTANGLE,
                new Rectangle(CONSTITUTION_RECTANGLE.x - 7, (int)CONSTITUTION_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle(CONSTITUTION_RECTANGLE.x - 7, (int)CONSTITUTION_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle(CONSTITUTION_RECTANGLE.x - 7, (int)CONSTITUTION_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle(CONSTITUTION_RECTANGLE.x - 7, (int)CONSTITUTION_RECTANGLE.getCenterY()+75, 150, 23)),

        AGILITY(AGILITY_RECTANGLE,
                new Rectangle(AGILITY_RECTANGLE.x - 7, (int)AGILITY_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle(AGILITY_RECTANGLE.x - 7, (int)AGILITY_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle(AGILITY_RECTANGLE.x - 7, (int)AGILITY_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle(AGILITY_RECTANGLE.x - 7, (int)AGILITY_RECTANGLE.getCenterY()+75, 150, 23)),

        HERBLORE(HERBLORE_RECTANGLE,
                new Rectangle(HERBLORE_RECTANGLE.x - 7, (int)HERBLORE_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle(HERBLORE_RECTANGLE.x - 7, (int)HERBLORE_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle(HERBLORE_RECTANGLE.x - 7, (int)HERBLORE_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle(HERBLORE_RECTANGLE.x - 7, (int)HERBLORE_RECTANGLE.getCenterY()+75, 150, 23)),

        THIEVING(THIEVING_RECTANGLE,
                new Rectangle(THIEVING_RECTANGLE.x - 7, (int)THIEVING_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle(THIEVING_RECTANGLE.x - 7, (int)THIEVING_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle(THIEVING_RECTANGLE.x - 7, (int)THIEVING_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle(THIEVING_RECTANGLE.x - 7, (int)THIEVING_RECTANGLE.getCenterY()+75, 150, 23)),

        CRAFTING(CRAFTING_RECTANGLE,
                new Rectangle(CRAFTING_RECTANGLE.x - 7, (int)CRAFTING_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle(CRAFTING_RECTANGLE.x - 7, (int)CRAFTING_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle(CRAFTING_RECTANGLE.x - 7, (int)CRAFTING_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle(CRAFTING_RECTANGLE.x - 7, (int)CRAFTING_RECTANGLE.getCenterY()+75, 150, 23)),

        FLETCHING(FLETCHING_RECTANGLE,
                new Rectangle(FLETCHING_RECTANGLE.x - 7, (int)FLETCHING_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle(FLETCHING_RECTANGLE.x - 7, (int)FLETCHING_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle(FLETCHING_RECTANGLE.x - 7, (int)FLETCHING_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle(FLETCHING_RECTANGLE.x - 7, (int)FLETCHING_RECTANGLE.getCenterY()+75, 150, 23)),

        SLAYER(SLAYER_RECTANGLE,
                new Rectangle(SLAYER_RECTANGLE.x - 7, (int)SLAYER_RECTANGLE.getCenterY()+38, 150, 23),
                new Rectangle(SLAYER_RECTANGLE.x - 7, (int)SLAYER_RECTANGLE.getCenterY()+48, 150, 23),
                new Rectangle(SLAYER_RECTANGLE.x - 7, (int)SLAYER_RECTANGLE.getCenterY()+65, 150, 23),
                new Rectangle(SLAYER_RECTANGLE.x - 7, (int)SLAYER_RECTANGLE.getCenterY()+75, 150, 23)),

        //column 3

        MINING(MINING_RECTANGLE,
                new Rectangle(MINING_RECTANGLE.x - 110, (int)MINING_RECTANGLE.getCenterY()+38, 175, 23),
                new Rectangle(MINING_RECTANGLE.x - 110, (int)MINING_RECTANGLE.getCenterY()+48, 175, 23),
                new Rectangle(MINING_RECTANGLE.x - 110, (int)MINING_RECTANGLE.getCenterY()+65, 175, 23),
                new Rectangle(MINING_RECTANGLE.x - 110, (int)MINING_RECTANGLE.getCenterY()+80, 175, 23)),

        SMITHING(SMITHING_RECTANGLE,
                new Rectangle(SMITHING_RECTANGLE.x - 110, (int)SMITHING_RECTANGLE.getCenterY()+38, 175, 23),
                new Rectangle(SMITHING_RECTANGLE.x - 110, (int)SMITHING_RECTANGLE.getCenterY()+48, 175, 23),
                new Rectangle(SMITHING_RECTANGLE.x - 110, (int)SMITHING_RECTANGLE.getCenterY()+65, 175, 23),
                new Rectangle(SMITHING_RECTANGLE.x - 110, (int)SMITHING_RECTANGLE.getCenterY()+80, 175, 23)),

        FISHING(FISHING_RECTANGLE,
                new Rectangle(FISHING_RECTANGLE.x - 110, (int)FISHING_RECTANGLE.getCenterY()+38, 175, 23),
                new Rectangle(FISHING_RECTANGLE.x - 110, (int)FISHING_RECTANGLE.getCenterY()+48, 175, 23),
                new Rectangle(FISHING_RECTANGLE.x - 110, (int)FISHING_RECTANGLE.getCenterY()+65, 175, 23),
                new Rectangle(FISHING_RECTANGLE.x - 110, (int)FISHING_RECTANGLE.getCenterY()+80, 175, 23)),

        COOKING(COOKING_RECTANGLE,
                new Rectangle(COOKING_RECTANGLE.x - 110, (int)COOKING_RECTANGLE.getCenterY()+38, 175, 23),
                new Rectangle(COOKING_RECTANGLE.x - 110, (int)COOKING_RECTANGLE.getCenterY()+48, 175, 23),
                new Rectangle(COOKING_RECTANGLE.x - 110, (int)COOKING_RECTANGLE.getCenterY()+65, 175, 23),
                new Rectangle(COOKING_RECTANGLE.x - 110, (int)COOKING_RECTANGLE.getCenterY()+80, 175, 23)),

        FIREMAKING(FIREMAKING_RECTANGLE,
                new Rectangle(FIREMAKING_RECTANGLE.x - 110, (int)FIREMAKING_RECTANGLE.getCenterY()+38, 175, 23),
                new Rectangle(FIREMAKING_RECTANGLE.x - 110, (int)FIREMAKING_RECTANGLE.getCenterY()+48, 175, 23),
                new Rectangle(FIREMAKING_RECTANGLE.x - 110, (int)FIREMAKING_RECTANGLE.getCenterY()+65, 175, 23),
                new Rectangle(FIREMAKING_RECTANGLE.x - 110, (int)FIREMAKING_RECTANGLE.getCenterY()+80, 175, 23)),

        WOODCUTTING(WOODCUTTING_RECTANGLE,
                new Rectangle(WOODCUTTING_RECTANGLE.x - 110, (int)WOODCUTTING_RECTANGLE.getCenterY()+38, 175, 23),
                new Rectangle(WOODCUTTING_RECTANGLE.x - 110, (int)WOODCUTTING_RECTANGLE.getCenterY()+48, 175, 23),
                new Rectangle(WOODCUTTING_RECTANGLE.x - 110, (int)WOODCUTTING_RECTANGLE.getCenterY()+65, 175, 23),
                new Rectangle(WOODCUTTING_RECTANGLE.x - 110, (int)WOODCUTTING_RECTANGLE.getCenterY()+80, 175, 23)),

        FARMING(FARMING_RECTANGLE,
                new Rectangle(FARMING_RECTANGLE.x - 110, (int)FARMING_RECTANGLE.getCenterY()+38, 175, 23),
                new Rectangle(FARMING_RECTANGLE.x - 110, (int)FARMING_RECTANGLE.getCenterY()+48, 175, 23),
                new Rectangle(FARMING_RECTANGLE.x - 110, (int)FARMING_RECTANGLE.getCenterY()+65, 175, 23),
                new Rectangle(FARMING_RECTANGLE.x - 110, (int)FARMING_RECTANGLE.getCenterY()+80, 175, 23));

        public Rectangle statRect;
        public Rectangle levelRect;
        public Rectangle currentXP;
        public Rectangle nextLevel;
        public Rectangle remainder;

        stats(Rectangle statRec, Rectangle levelForSkill, Rectangle XP, Rectangle toLvl, Rectangle left) {
            statRect = statRec;
            levelRect = levelForSkill;
            currentXP = XP;
            nextLevel = toLvl;
            remainder = left;

        }
        //TODO write methods to make life easier for scripters
    }

    public static boolean isOpen() {
        return Tabs.getCurrentTab().equals("Stats");
    }

    public static boolean open() {
        return Tabs.setTab("Stats");
    }
}
