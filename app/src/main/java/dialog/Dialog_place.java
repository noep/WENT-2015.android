package dialog;

/**
 * Created by NOEP on 15. 7. 6..
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class Dialog_place extends DialogFragment implements DialogInterface.OnClickListener {

    public static final int REQUEST_CODE_SEARCH = 3;
    public static final int REQUEST_CODE_GPS = 4;

    private DialogInterface.OnClickListener listener;
    public void setOnClickListener(DialogInterface.OnClickListener listener) { this.listener = listener; }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("WENT");
        builder.setMessage("choose your location");
        builder.setPositiveButton("Search", this);
        builder.setNeutralButton("By GPS", this);
        builder.setNegativeButton("Cancel", this);
        builder.setCancelable(false);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (listener == null)
            return;

        listener.onClick(dialog, which);
    }
}