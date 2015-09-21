package com.ariana.shahre_ma.Bazarche;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

/**
 * Created by ariana2 on 9/21/2015...
 */
public class MoneyTextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;

    public MoneyTextWatcher(EditText editText) {
        editTextWeakReference = new WeakReference<EditText>(editText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        EditText editText = editTextWeakReference.get();
        if (editText == null) return;
        String s = editable.toString();
        editText.removeTextChangedListener(this);
        try {
            String givenstring = s.toString();
            Long longval;
            if (givenstring.contains(",")) {
                givenstring = givenstring.replaceAll(",", "");
            }
            longval = Long.parseLong(givenstring);
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedString = formatter.format(longval);
            editText.setText(formattedString);
            editText.setSelection(editText.getText().length());
            // to place the cursor at the end of text
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        editText.addTextChangedListener(this);
    }
}