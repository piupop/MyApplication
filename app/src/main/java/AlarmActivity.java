import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.provider.AlarmClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.lifeistech.android.eventreminder.R;

/**
 * Created by Marina Hayashi on 2017/05/17.
 */

public class AlarmActivity extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // ボタンの取得
        Button btn = (Button)findViewById(R.id.btn);

        // ボタンをリスナーに登録
        btn.setOnClickListener(this);

    }

    // ボタン押下時の処理
    public void onClick(View v) {

// TimePickerインスタンスを取得
        TimePicker timePicker = (TimePicker)findViewById(R.id.time_picker);

// 設定時刻の時間を取得
        int hour = timePicker.getCurrentHour();

// 設定時刻の分を取得
        int min = timePicker.getCurrentMinute();

        // アラームを設定するインテントを取得する
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);

        // アラーム時刻を設定する
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, min);

        // テキストボックスを取得
        EditText editText = (EditText)findViewById(R.id.msg);

        // 入力されたメッセージを取得
        String msg = editText.getText().toString();

        // アラームメッセージを設定する
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, msg);

        // インテントを発行する
        startActivity(intent);

    }
}