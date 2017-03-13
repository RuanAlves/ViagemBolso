package br.com.viagembolso.utilis;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Class com vários utilitários para o sistema
 *
 * @author Ruan Alves
 */
public class Utils {

    private static NumberFormat nf = NumberFormat.getInstance();

    /**
     * @param precoDouble
     * @return
     * @author Ruan Alves
     */
    public static double converterDoubleDoisDecimais(double precoDouble) {
        try {
            DecimalFormat fmt = new DecimalFormat("0.00");
            String string = fmt.format(precoDouble);
            String[] part = string.split("[,]");
            String string2 = part[0] + "." + part[1];
            double preco = Double.parseDouble(string2);
            return preco;
        } catch (Exception e) {
            return precoDouble;
        }

    }

    public static String getMaskMoney(Double valor) {

        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        if (valor != null) {
            return nf.format(valor);
        } else {
            return null;
        }
    }

    public static String converterDoubleDoisDecimais3(Double precoDouble) {
        Locale.setDefault(new Locale("pt", "BR"));
        DecimalFormat fmt = new DecimalFormat("#,##0.00");
        String string = fmt.format(precoDouble);
        /*   String[] part = string.split("[,]");
        String string2 = part[0]+"."+part[1];  */

        return string;
    }

    public static double converterDoubleDoisDecimais2(double precoDouble) {
        Locale.setDefault(new Locale("pt", "BR"));
        DecimalFormat fmt = new DecimalFormat("0.00");
        String string = fmt.format(precoDouble);
        String[] part = string.split("[,]");
        String string2 = part[0] + "." + part[1];
        double preco = Double.parseDouble(string2);
        return preco;
    }

    /**
     * Pega os estados
     *
     * @return
     * @author Ruan Alves
     */
    public static String[] getEstados() {
        String[] est = new String[]{"", "AC", "AL", "AP", "AM", "BA", "CE", "DF",
                "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE",
                "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

        return est;
    }

    public static TextWatcher mask(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = Utils.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    /**
     * Retira os pontos, traços de uma string
     *
     * @param s
     * @return
     * @author Ruan Alves
     */
    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    /**
     * metodo respons�vel por validadar campos null
     *
     * @param pView
     * @param pMessage
     * @return
     */
    public static boolean validateNotNull(View pView, String pMessage) {
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    return true;
                }
            }

            // em qualquer outra condi��o � gerado um erro
            edText.setError(pMessage);
            edText.setFocusable(true);
            edText.requestFocus();
            return false;
        }
        return false;
    }

    public static boolean validateEmail(String txtEmail) {
        if (TextUtils.isEmpty(txtEmail)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches();
        }
    }

    public static void setError(EditText edText, String msg) {
        if (!edText.isFocused()) {
            if (edText.getText().toString().trim().length() == 0) {
                edText.setError(msg);
                //etCliente.requestFocus();
            } else {
                edText.setError(null);
            }
        }
    }

    /**
     * Método que passa uma data para String, convertendo no formato dd/MM/yyyy
     *
     * @param data
     * @return
     * @author Ruan Alves
     */
    public static String formata_dd_MM_yyyy(Date data) {

        String DataNascDb = null;
        DataNascDb = new SimpleDateFormat("dd/MM/yyyy").format(data);

        return DataNascDb;
    }

    /**
     * Método que transforma uma String em Data
     *
     * @param data
     * @return
     * @author Ruan Alves
     */
    public static String formataDataString_YYYY_MM_DD(Date data) {

        String DataNascDb = null;
        DataNascDb = new SimpleDateFormat("yyyy-MM-dd").format(data);

        return DataNascDb;
    }

    /**
     * metodo que verifica se a data é maior que o outro
     *
     * @param data01
     * @param data02
     * @return
     * @author Ruan Alves
     */
    public static Boolean compareDatas(Date data01, Date data02) {
        if (data01.compareTo(data02) >= 1) {
            //System.out.println("data01 Maior");
            return true;
        } else {
            //System.out.println("data02 Maior");
            return false;
        }
    }

    /**
     * @param dataLow  The lowest date
     * @param dataHigh The highest date
     * @return int
     * @author Ruan Alves
     * Método para comparar as das e retornar o numero de dias de diferença entre elas
     * <p/>
     * Compare two date and return the difference between them in days.
     */
    public static int dataDiff(Date dataLow, Date dataHigh) {

        GregorianCalendar startTime = new GregorianCalendar();
        GregorianCalendar endTime = new GregorianCalendar();

        GregorianCalendar curTime = new GregorianCalendar();
        GregorianCalendar baseTime = new GregorianCalendar();

        startTime.setTime(dataLow);
        endTime.setTime(dataHigh);

        int dif_multiplier = 1;

        // Verifica a ordem de inicio das datas
        if (dataLow.compareTo(dataHigh) < 0) {
            baseTime.setTime(dataHigh);
            curTime.setTime(dataLow);
            dif_multiplier = 1;
        } else {
            baseTime.setTime(dataLow);
            curTime.setTime(dataHigh);
            dif_multiplier = -1;
        }

        int result_years = 0;
        int result_months = 0;
        int result_days = 0;

        // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando
        // no total de dias. Ja leva em consideracao ano bissesto
        while (curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||
                curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)) {

            int max_day = curTime.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            result_months += max_day;
            curTime.add(GregorianCalendar.MONTH, 1);

        }

        // Marca que é um saldo negativo ou positivo
        result_months = result_months * dif_multiplier;


        // Retirna a diferenca de dias do total dos meses
        result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));

        return result_years + result_months + result_days;
    }

    /**
     * Método que formata a data no Seguinte Formato dd/MM/yyyy - HH:mm:ss
     *
     * @return
     */
    public static String retornaDateFormatadaDDMMYY_HHmmss(Date data) {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data);

    }

    public static Date formataStringData_YYYY_MM_DD_HHMMYYYY(String data) throws Exception {

        if (data == null || data.equals("")) return null;
        Date date = null;

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = (Date) formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }

        return date;

    }

    public static Date formataStringData_YYYY_MM_DD(String data) throws Exception {

        if (data == null || data.equals("")) return null;
        Date date = null;

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = (Date) formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }

        return date;

    }

}
