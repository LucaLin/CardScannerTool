package com.example.r30_a.cardscannertool;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_FOR_SCAN = 101;

    Button btnScan;
    TextView txvCardnum,txvExpireDay,txvCVV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = (Button)findViewById(R.id.btnScan);
        txvCardnum = (TextView)findViewById(R.id.txvCardnum);
        txvExpireDay = (TextView)findViewById(R.id.txvExpireDay);
        txvCVV = (TextView)findViewById(R.id.txvCVV);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardIOActivity.class)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY,true)
                        .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY,true)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV,true)//回傳識別碼
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME,true)//回傳持卡人名稱
                        .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE,"ch")//設定工具語言
                        .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)//掃描框顏色
                        //.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true)//去除鍵盤輸入
                        .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true);//是否回傳卡片圖像回來
                startActivityForResult(intent,REQUEST_FOR_SCAN);
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_FOR_SCAN && data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)){

            if(data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)){
                CreditCard card = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                txvCardnum.setText(card.getRedactedCardNumber());

                if(card.isExpiryValid()){
                    txvExpireDay.setText(card.expiryMonth + " / "  + card.expiryYear);
                }
                if(card.cvv != null){
                    txvCVV.setText( card.cvv.toString());
                }
                if(card.postalCode != null){

                }
            }else {

            }

        }
    }
}