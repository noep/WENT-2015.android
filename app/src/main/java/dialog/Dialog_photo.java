package dialog;

/**
 * Created by NOEP on 15. 7. 6..
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class Dialog_photo extends DialogFragment implements DialogInterface.OnClickListener {

    public static final int REQUEST_CODE_CAPTURE = 1;
    public static final int REQUEST_CODE_GALLERY = 2;

    private DialogInterface.OnClickListener listener;
    public void setOnClickListener(DialogInterface.OnClickListener listener) { this.listener = listener; }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Photo");
        builder.setMessage("Where do you want pick one ?");
        builder.setPositiveButton("Camera", this);
        builder.setNeutralButton("Gallery", this);
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