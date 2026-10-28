package alumnet.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * Central place for the app's colors and fonts.
 * Keeping this in one file makes it easy to keep every screen consistent.
 */
public class UITheme {

    // Theme colors: Deep Navy, Indigo, Electric Blue, White, Light Grey
    public static final Color NAVY = new Color(17, 24, 57);
    public static final Color INDIGO = new Color(63, 55, 201);
    public static final Color ELECTRIC_BLUE = new Color(0, 122, 255);
    public static final Color WHITE = Color.WHITE;
    public static final Color LIGHT_GREY = new Color(244, 245, 250);
    public static final Color MID_GREY = new Color(210, 213, 224);
    public static final Color TEXT_DARK = new Color(30, 32, 46);
    public static final Color SUCCESS_GREEN = new Color(34, 177, 76);
    public static final Color WARNING_ORANGE = new Color(255, 140, 0);

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_STAT_NUMBER = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FONT_SIDEBAR = new Font("Segoe UI", Font.PLAIN, 14);
}
