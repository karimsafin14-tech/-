
package com.rofl.bedwars;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {
    EditText kills, deaths, falls, beds, wins;
    TextView result;

    int bg = Color.rgb(18, 19, 31);
    int panel = Color.rgb(35, 37, 57);
    int accent = Color.rgb(255, 76, 120);

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bg);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 28, 24, 28);
        scroll.addView(root);
        setContentView(scroll);

        TextView title = text("💀 КАЛЬКУЛЯТОР ПОЗОРА", 24, Color.WHITE, true);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView subtitle = text(
            "BEDWARS EDITION • АНАЛИЗ ТВОЕГО ФИАСКО",
            12, Color.LTGRAY, false
        );
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(16, 16, 16, 16);
        card.setBackground(round(panel, 18));
        root.addView(card);

        kills = field(card, "Убийства");
        deaths = field(card, "Смерти");
        falls = field(card, "Падения в пустоту");
        beds = field(card, "Сломанные кровати");
        wins = field(card, "Победы");

        Button calc = new Button(this);
        calc.setText("РАССЧИТАТЬ ПОЗОР");
        calc.setTextColor(Color.WHITE);
        calc.setBackground(round(accent, 12));
        card.addView(calc);

        result = text("Твой вердикт появится здесь...", 17, Color.WHITE, true);
        result.setGravity(Gravity.CENTER);
        result.setPadding(16, 24, 16, 24);
        root.addView(result);

        calc.setOnClickListener(v -> calculate());
    }

    EditText field(LinearLayout parent, String label) {
        TextView t = text(label, 14, Color.WHITE, true);
        parent.addView(t);

        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setInputType(2);
        e.setHint("Введи число");
        e.setTextColor(Color.WHITE);
        e.setHintTextColor(Color.GRAY);
        parent.addView(e);
        return e;
    }

    int number(EditText e) {
        try {
            return Math.max(0, Integer.parseInt(
                e.getText().toString().trim()
            ));
        } catch (Exception ex) {
            return 0;
        }
    }

    void calculate() {
        int k = number(kills);
        int d = number(deaths);
        int f = number(falls);
        int b = number(beds);
        int w = number(wins);

        int score = Math.max(0, d * 7 + f * 12 - k * 3 - b * 15 - w * 10);

        String verdict;
        if (f >= 5) {
            verdict = "🕳️ ГРАВИТАЦИЯ — ТВОЙ ГЛАВНЫЙ ВРАГ!";
        } else if (d >= k + 5) {
            verdict = "💀 ТЫ ХОДЯЧИЙ ЛУТБОКС!";
        } else if (b == 0 && w == 0) {
            verdict = "🛏️ КРОВАТИ ЦЕЛЫ, ПОБЕД НЕТ. ТУРИСТ!";
        } else if (b >= 3 || w >= 3) {
            verdict = "👑 МАШИНА BEDWARS! ПОЗОР ОТМЕНЯЕТСЯ.";
        } else if (k > d * 2 && k > 0) {
            verdict = "⚔️ ТЫ — КОШМАР СОПЕРНИКОВ!";
        } else {
            verdict = "🤡 СРЕДНЯЧОК. ЕСТЬ КУДА РАСТИ!";
        }

        String level = score >= 60 ? "КРИТИЧЕСКИЙ ПОЗОР ☠️"
            : score >= 30 ? "ПОЗОР 💀"
            : score >= 10 ? "ЛЁГКИЙ КРИНЖ 😬"
            : "ПОЧТИ ЧИСТ 💎";

        result.setText(level + "\n\n" + verdict
            + "\n\nИндекс позора: " + score);
    }

    TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(null, Typeface.BOLD);
        return t;
    }

    GradientDrawable round(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }
}
