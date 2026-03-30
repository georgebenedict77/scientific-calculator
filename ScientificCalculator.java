/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.scientificcalculator;

/**
 *
 * @author BENJOJI77
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScientificCalculator extends JFrame implements ActionListener {

    // ── display ──────────────────────────────────────────────────
    JLabel historyLabel  = new JLabel(" ", SwingConstants.RIGHT);
    JLabel resultLabel   = new JLabel("0", SwingConstants.RIGHT);
    JLabel memoryLabel   = new JLabel(" ", SwingConstants.LEFT);

    // ── trig buttons (labels change with HYP/INV) ────────────────
    JButton btnSin, btnCos, btnTan;

    // ── mode toggles ─────────────────────────────────────────────
    JButton btnDeg, btnRad, btnGrad, btnHyp, btnInv;

    // ── state ────────────────────────────────────────────────────
    String  expression = "";
    double  num1       = 0;
    double  num2       = 0;
    double  memory     = 0;
    double  lastAnswer = 0;
    String  operator   = "";
    String  angleMode  = "DEG";
    boolean hypMode    = false;
    boolean invMode    = false;
    boolean freshCalc  = false;

    // ── colors ───────────────────────────────────────────────────
    Color DARK       = new Color(10,  10,  26);
    Color DARKER     = new Color(5,   5,   16);
    Color INDIGO     = new Color(79,  70,  229);
    Color BTN_NUM    = new Color(20,  20,  40);
    Color BTN_OP     = new Color(26,  26,  69);
    Color BTN_FN     = new Color(12,  12,  34);
    Color BTN_EQ     = new Color(79,  70,  229);
    Color BTN_AC     = new Color(58,  10,  10);
    Color BTN_MEM    = new Color(13,  13,  8);
    Color BTN_CONST  = new Color(8,   20,  8);
    Color TXT_NUM    = new Color(200, 200, 240);
    Color TXT_OP     = new Color(136, 136, 238);
    Color TXT_FN     = new Color(123, 156, 255);
    Color TXT_FN2    = new Color(167, 139, 250);
    Color TXT_AC     = new Color(255, 119, 119);
    Color TXT_MEM    = new Color(212, 184, 74);
    Color TXT_CONST  = new Color(74,  222, 128);
    Color TXT_DIM    = new Color(60,  60,  100);
    Color TXT_RESULT = new Color(224, 224, 255);
    Color TXT_HIST   = new Color(80,  80,  120);

    // ─────────────────────────────────────────────────────────────
    ScientificCalculator() {
        setTitle("benjoji77 · Scientific Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(0, 8));
        root.setBackground(DARK);
        root.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        root.add(makeTopBar(),  BorderLayout.NORTH);
        root.add(makeDisplay(), BorderLayout.CENTER);
        root.add(makeKeypad(),  BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ─────────────────── TOP BAR ─────────────────────────────────
    JPanel makeTopBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(DARK);

        JLabel brand = new JLabel("BENJOJI77");
        brand.setFont(new Font("Monospaced", Font.BOLD, 13));
        brand.setForeground(INDIGO);

        JLabel sub = new JLabel("SCIENTIFIC CALCULATOR v1.0");
        sub.setFont(new Font("Monospaced", Font.PLAIN, 9));
        sub.setForeground(TXT_DIM);

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setBackground(DARK);
        left.add(brand);
        left.add(sub);

        JLabel builtBy = new JLabel("built by benjoji77", SwingConstants.RIGHT);
        builtBy.setFont(new Font("Monospaced", Font.PLAIN, 9));
        builtBy.setForeground(TXT_DIM);

        p.add(left,    BorderLayout.WEST);
        p.add(builtBy, BorderLayout.EAST);
        return p;
    }

    // ─────────────────── DISPLAY ─────────────────────────────────
    JPanel makeDisplay() {
        JPanel modeRow = new JPanel(new GridLayout(1, 5, 4, 0));
        modeRow.setBackground(new Color(8, 8, 20));
        modeRow.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));

        btnDeg  = modeBtn("DEG");
        btnRad  = modeBtn("RAD");
        btnGrad = modeBtn("GRAD");
        btnHyp  = modeBtn("HYP");
        btnInv  = modeBtn("INV");

        btnDeg.setBackground(INDIGO);
        btnDeg.setForeground(Color.WHITE);

        btnDeg.addActionListener(e  -> switchAngle("DEG"));
        btnRad.addActionListener(e  -> switchAngle("RAD"));
        btnGrad.addActionListener(e -> switchAngle("GRAD"));
        btnHyp.addActionListener(e  -> toggleHyp());
        btnInv.addActionListener(e  -> toggleInv());

        for (JButton b : new JButton[]{btnDeg, btnRad, btnGrad, btnHyp, btnInv})
            modeRow.add(b);

        historyLabel.setFont(new Font("Monospaced", Font.PLAIN, 10));
        historyLabel.setForeground(TXT_HIST);

        resultLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        resultLabel.setForeground(TXT_RESULT);

        memoryLabel.setFont(new Font("Monospaced", Font.PLAIN, 9));
        memoryLabel.setForeground(INDIGO);

        JPanel screen = new JPanel(new GridLayout(3, 1));
        screen.setBackground(DARKER);
        screen.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        screen.add(historyLabel);
        screen.add(resultLabel);
        screen.add(memoryLabel);

        JPanel display = new JPanel(new BorderLayout());
        display.setBackground(DARKER);
        display.setBorder(BorderFactory.createLineBorder(new Color(26, 26, 58)));
        display.add(modeRow, BorderLayout.NORTH);
        display.add(screen,  BorderLayout.CENTER);
        return display;
    }

    JButton modeBtn(String label) {
        JButton b = new JButton(label);
        b.setFont(new Font("Monospaced", Font.BOLD, 9));
        b.setBackground(new Color(8, 8, 20));
        b.setForeground(TXT_DIM);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ─────────────────── KEYPAD ──────────────────────────────────
    JPanel makeKeypad() {
        JPanel p = new JPanel(new GridLayout(0, 5, 5, 5));
        p.setBackground(DARK);

        p.add(key("MS",   BTN_MEM,   TXT_MEM,   10));
        p.add(key("MR",   BTN_MEM,   TXT_MEM,   10));
        p.add(key("M+",   BTN_MEM,   TXT_MEM,   10));
        p.add(key("M-",   BTN_MEM,   TXT_MEM,   10));
        p.add(key("MC",   BTN_MEM,   TXT_MEM,   10));

        btnSin = key("sin", BTN_FN, TXT_FN, 11);
        btnCos = key("cos", BTN_FN, TXT_FN, 11);
        btnTan = key("tan", BTN_FN, TXT_FN, 11);
        p.add(btnSin);
        p.add(btnCos);
        p.add(btnTan);
        p.add(key("nPr",  BTN_FN,    TXT_FN2,   11));
        p.add(key("nCr",  BTN_FN,    TXT_FN2,   11));

        p.add(key("x²",   BTN_FN,    TXT_FN2,   11));
        p.add(key("x³",   BTN_FN,    TXT_FN2,   11));
        p.add(key("xʸ",   BTN_FN,    TXT_FN2,   11));
        p.add(key("√",    BTN_FN,    TXT_FN2,   11));
        p.add(key("∛",    BTN_FN,    TXT_FN2,   11));

        p.add(key("log",  BTN_FN,    TXT_FN,    11));
        p.add(key("ln",   BTN_FN,    TXT_FN,    11));
        p.add(key("log₂", BTN_FN,    TXT_FN,    11));
        p.add(key("10ˣ",  BTN_FN,    TXT_FN2,   11));
        p.add(key("eˣ",   BTN_FN,    TXT_FN2,   11));

        p.add(key("π",    BTN_CONST, TXT_CONST, 13));
        p.add(key("e",    BTN_CONST, TXT_CONST, 13));
        p.add(key("n!",   BTN_FN,    TXT_FN2,   11));
        p.add(key("|x|",  BTN_FN,    TXT_FN2,   11));
        p.add(key("1/x",  BTN_FN,    TXT_FN2,   11));

        p.add(key("(",    BTN_OP,    TXT_OP,    13));
        p.add(key(")",    BTN_OP,    TXT_OP,    13));
        p.add(key("%",    BTN_FN,    TXT_FN2,   11));
        p.add(key("±",    BTN_FN,    TXT_FN2,   11));
        p.add(key("⌫",    BTN_AC,    TXT_AC,    13));

        p.add(key("7",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("8",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("9",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("÷",    BTN_OP,    TXT_OP,    15));
        p.add(key("AC",   BTN_AC,    TXT_AC,    12));

        p.add(key("4",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("5",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("6",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("×",    BTN_OP,    TXT_OP,    15));
        p.add(key("mod",  BTN_FN,    TXT_FN2,   10));

        p.add(key("1",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("2",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("3",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("−",    BTN_OP,    TXT_OP,    15));
        p.add(key("ANS",  BTN_FN,    TXT_FN2,   10));

        p.add(key("0",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("00",   BTN_NUM,   TXT_NUM,   13));
        p.add(key(".",    BTN_NUM,   TXT_NUM,   15));
        p.add(key("+",    BTN_OP,    TXT_OP,    15));
        p.add(key("=",    BTN_EQ,    Color.WHITE, 16));

        // signature
        JPanel sigBar = new JPanel(new BorderLayout());
        sigBar.setBackground(new Color(7, 7, 15));
        sigBar.setBorder(BorderFactory.createEmptyBorder(6, 8, 2, 8));

        JLabel sigL = new JLabel("BENJOJI77 © GEORGE BENEDICT");
        JLabel sigR = new JLabel("CS + DESIGN · KENYA", SwingConstants.RIGHT);
        for (JLabel l : new JLabel[]{sigL, sigR}) {
            l.setFont(new Font("Monospaced", Font.PLAIN, 8));
            l.setForeground(new Color(40, 40, 70));
        }
        sigBar.add(sigL, BorderLayout.WEST);
        sigBar.add(sigR, BorderLayout.EAST);

        JPanel full = new JPanel(new BorderLayout(0, 6));
        full.setBackground(DARK);
        full.add(p,      BorderLayout.CENTER);
        full.add(sigBar, BorderLayout.SOUTH);
        return full;
    }

    JButton key(String label, Color bg, Color fg, int fontSize) {
        JButton b = new JButton(label);
        b.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(62, 44));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(this);
        Color base = bg;
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e)  { b.setBackground(base.brighter()); }
            public void mouseExited(MouseEvent e)   { b.setBackground(base); }
            public void mousePressed(MouseEvent e)  { b.setBackground(base.darker()); }
            public void mouseReleased(MouseEvent e) { b.setBackground(base); }
        });
        return b;
    }

    // ─────────────────── ACTION HANDLER ──────────────────────────
    public void actionPerformed(ActionEvent e) {
        String cmd = ((JButton) e.getSource()).getText();

        if (cmd.matches("[0-9]") || cmd.equals("00")) {
            if (freshCalc) { expression = ""; freshCalc = false; }
            expression += cmd;
            show(expression);
            return;
        }

        if (cmd.equals(".")) {
            if (!expression.contains(".")) expression += ".";
            show(expression);
            return;
        }

        if (cmd.equals("+") || cmd.equals("−") || cmd.equals("×") || cmd.equals("÷")) {
            if (!expression.isEmpty()) {
                num1     = toDouble(expression);
                operator = cmd;
                expression = "";
                freshCalc  = false;
            }
            return;
        }

        if (cmd.equals("=")) {
            if (operator.isEmpty() || expression.isEmpty()) return;
            num2 = toDouble(expression);
            double result = 0;
            switch (operator) {
                case "+":   result = num1 + num2; break;
                case "−":   result = num1 - num2; break;
                case "×":   result = num1 * num2; break;
                case "÷":   result = num2 != 0 ? num1 / num2 : Double.NaN; break;
                case "xʸ":  result = Math.pow(num1, num2); break;
                case "nPr": result = factorial((int)num1) / factorial((int)(num1 - num2)); break;
                case "nCr": result = factorial((int)num1) / (factorial((int)num2) * factorial((int)(num1 - num2))); break;
                case "mod": result = num1 % num2; break;
            }
            historyLabel.setText(format(num1) + " " + operator + " " + format(num2) + " =");
            lastAnswer = result;
            expression = format(result);
            operator   = "";
            freshCalc  = true;
            show(expression);
            return;
        }

        if (cmd.equals("AC"))  { expression = ""; operator = ""; num1 = 0; freshCalc = false; historyLabel.setText(" "); show("0"); return; }
        if (cmd.equals("⌫"))   { if (!expression.isEmpty()) expression = expression.substring(0, expression.length()-1); show(expression.isEmpty() ? "0" : expression); return; }
        if (cmd.equals("±"))   { if (!expression.isEmpty()) expression = expression.startsWith("-") ? expression.substring(1) : "-" + expression; show(expression); return; }
        if (cmd.equals("ANS")) { expression = format(lastAnswer); show(expression); return; }

        if (cmd.equals("MS"))  { memory = toDouble(expression); memoryLabel.setText("M: " + format(memory)); return; }
        if (cmd.equals("MR"))  { expression = format(memory); show(expression); return; }
        if (cmd.equals("M+"))  { memory += toDouble(expression); memoryLabel.setText("M: " + format(memory)); return; }
        if (cmd.equals("M-"))  { memory -= toDouble(expression); memoryLabel.setText("M: " + format(memory)); return; }
        if (cmd.equals("MC"))  { memory = 0; memoryLabel.setText(" "); return; }

        if (cmd.equals("π"))   { expression = String.valueOf(Math.PI); show(expression); return; }
        if (cmd.equals("e"))   { expression = String.valueOf(Math.E);  show(expression); return; }

        // two-arg functions — store first value and wait
        if (cmd.equals("xʸ") || cmd.equals("nPr") || cmd.equals("nCr") || cmd.equals("mod")) {
            if (!expression.isEmpty()) { num1 = toDouble(expression); operator = cmd; expression = ""; freshCalc = false; }
            return;
        }

        // single-arg scientific functions
        if (!expression.isEmpty()) {
            double val    = toDouble(expression);
            double result = singleArgFn(cmd, val);
            historyLabel.setText(cmd + "(" + format(val) + ") =");
            lastAnswer = result;
            expression = format(result);
            freshCalc  = true;
            show(expression);
        }
    }

    double singleArgFn(String fn, double v) {
        switch (fn) {
            case "sin":   return Math.sin(toRad(v));
            case "cos":   return Math.cos(toRad(v));
            case "tan":   return Math.tan(toRad(v));
            case "sin⁻¹": return fromRad(Math.asin(v));
            case "cos⁻¹": return fromRad(Math.acos(v));
            case "tan⁻¹": return fromRad(Math.atan(v));
            case "sinh":  return Math.sinh(v);
            case "cosh":  return Math.cosh(v);
            case "tanh":  return Math.tanh(v);
            case "asinh": return Math.log(v + Math.sqrt(v * v + 1));
            case "acosh": return Math.log(v + Math.sqrt(v * v - 1));
            case "atanh": return 0.5 * Math.log((1 + v) / (1 - v));
            case "x²":   return v * v;
            case "x³":   return v * v * v;
            case "√":    return Math.sqrt(v);
            case "∛":    return Math.cbrt(v);
            case "log":  return Math.log10(v);
            case "ln":   return Math.log(v);
            case "log₂": return Math.log(v) / Math.log(2);
            case "10ˣ":  return Math.pow(10, v);
            case "eˣ":   return Math.exp(v);
            case "n!":   return factorial((int) Math.abs(v));
            case "|x|":  return Math.abs(v);
            case "1/x":  return 1.0 / v;
            case "%":    return v / 100.0;
            default:     return v;
        }
    }

    // ─────────────────── ANGLE MODES ─────────────────────────────
    void switchAngle(String mode) {
        angleMode = mode;
        Color off = new Color(8, 8, 20);
        btnDeg.setBackground(mode.equals("DEG")   ? INDIGO : off); btnDeg.setForeground(mode.equals("DEG")   ? Color.WHITE : TXT_DIM);
        btnRad.setBackground(mode.equals("RAD")   ? INDIGO : off); btnRad.setForeground(mode.equals("RAD")   ? Color.WHITE : TXT_DIM);
        btnGrad.setBackground(mode.equals("GRAD") ? INDIGO : off); btnGrad.setForeground(mode.equals("GRAD") ? Color.WHITE : TXT_DIM);
    }

    void toggleHyp() {
        hypMode = !hypMode;
        btnHyp.setBackground(hypMode ? INDIGO : new Color(8, 8, 20));
        btnHyp.setForeground(hypMode ? Color.WHITE : TXT_DIM);
        refreshTrigLabels();
    }

    void toggleInv() {
        invMode = !invMode;
        btnInv.setBackground(invMode ? INDIGO : new Color(8, 8, 20));
        btnInv.setForeground(invMode ? Color.WHITE : TXT_DIM);
        refreshTrigLabels();
    }

    void refreshTrigLabels() {
        if (hypMode && invMode) { btnSin.setText("asinh"); btnCos.setText("acosh"); btnTan.setText("atanh"); }
        else if (hypMode)       { btnSin.setText("sinh");  btnCos.setText("cosh");  btnTan.setText("tanh"); }
        else if (invMode)       { btnSin.setText("sin⁻¹"); btnCos.setText("cos⁻¹"); btnTan.setText("tan⁻¹"); }
        else                    { btnSin.setText("sin");   btnCos.setText("cos");   btnTan.setText("tan"); }
    }

    double toRad(double v) {
        if (angleMode.equals("RAD"))  return v;
        if (angleMode.equals("GRAD")) return v * Math.PI / 200;
        return Math.toRadians(v);
    }

    double fromRad(double v) {
        if (angleMode.equals("RAD"))  return v;
        if (angleMode.equals("GRAD")) return v * 200 / Math.PI;
        return Math.toDegrees(v);
    }

    // ─────────────────── HELPERS ──────────────────────────────────
    double factorial(int n) {
        if (n > 170) return Double.POSITIVE_INFINITY;
        double r = 1;
        for (int i = 2; i <= n; i++) r *= i;
        return r;
    }

    double toDouble(String s) {
        try { return Double.parseDouble(s); }
        catch (Exception ex) { return 0; }
    }

    String format(double v) {
        if (Double.isNaN(v) || Double.isInfinite(v)) return "MATH ERROR";
        if (v == Math.floor(v) && Math.abs(v) < 1e15) return String.valueOf((long) v);
        return String.valueOf(Double.parseDouble(String.format("%.10g", v)));
    }

    void show(String val) {
        resultLabel.setText(val.isEmpty() ? "0" : val);
    }

    // ─────────────────── ENTRY POINT ─────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new ScientificCalculator();
        });
    }
}
