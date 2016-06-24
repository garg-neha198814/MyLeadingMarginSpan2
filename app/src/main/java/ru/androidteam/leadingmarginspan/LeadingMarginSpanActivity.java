package ru.androidteam.leadingmarginspan;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.LeadingMarginSpan.LeadingMarginSpan2;
import android.widget.ImageView;
import android.widget.TextView;

public class LeadingMarginSpanActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String text = getString(R.string.text);

        // Получаем иконку и ее ширину
        Drawable dIcon = getResources().getDrawable(R.drawable.icon);
        int leftMargin = dIcon.getIntrinsicWidth() + 10;

        // Устанавливаем иконку в R.id.icon
        ImageView icon = (ImageView) findViewById(R.id.icon);
        icon.setBackgroundDrawable(dIcon);

        SpannableString ss = new SpannableString(Html.fromHtml(text));
        System.out.println("height"+dIcon.getIntrinsicHeight());
        // Выставляем отступ для первых трек строк абазца
        ss.setSpan(new MyLeadingMarginSpan2(3, leftMargin), 0, ss.length(), 0);

        TextView messageView = (TextView) findViewById(R.id.message_view);
        messageView.setText(ss);
    }

    class MyLeadingMarginSpan2 implements LeadingMarginSpan2 {
        private int margin;
        private int lines;

        MyLeadingMarginSpan2(int lines, int margin) {
            this.margin = margin;
            this.lines = lines;
        }
       /* MyLeadingMarginSpan2(Context cc, int textSize, int height, int width) {
            System.out.println("textsize"+textSize);
            int pixelsInLine=(int) (textSize*cc.getResources().getDisplayMetrics().scaledDensity);
            System.out.println("pixels"+pixelsInLine);
            if (pixelsInLine>0 && height>0) {
              int no =pixelsInLine/(height+width);
                int rem = pixelsInLine%(height+width);
                if(rem>0) {
                    this.lines = no + 1;
                }
                else{
                    this.lines = no;
                }
                System.out.println("lines"+this.lines);
            } else  {
                this.lines=0;
            }
            this.margin=width; }*/

        /* Возвращает значение, на которе должен быть добавлен отступ */
        @Override
        public int getLeadingMargin(boolean first) {
            if (first) {
                /*
                 * Данный отступ будет применен к количеству строк
                 * возвращаемых getLeadingMarginLineCount()
                 */
                return margin;
            } else {
                // Отступ для всех остальных строк
                return 0;
            }
        }

        @Override
        public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, 
                int top, int baseline, int bottom, CharSequence text, 
                int start, int end, boolean first, Layout layout) {}

        /*
         * Возвращает количество строк, к которым должен быть применен
         * отступ возвращаемый методом getLeadingMargin(true)
         * Замечание. Отступ применяется только к N строкам одного параграфа.
         */
        @Override
        public int getLeadingMarginLineCount() {
            return lines;
        }
    };
}