//package com.walhalla.health.activity;
//
//import android.os.Bundle;
//import android.os.Vibrator;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.walhalla.health.BaseActivity;
//import com.walhalla.health.R;
//
//public class Calculator extends BaseActivity {
//
//    private boolean add;
//    private boolean div;
//    private boolean dot = false;
//    private Animation fade_out;
//    private LinearLayout ll0;
//    private LinearLayout ll1;
//    private LinearLayout ll2;
//    private LinearLayout ll3;
//    private LinearLayout ll4;
//    private LinearLayout ll5;
//    private LinearLayout ll6;
//    private LinearLayout ll7;
//    private LinearLayout ll8;
//    private LinearLayout ll9;
//    private LinearLayout lladd;
//    private LinearLayout lldiv;
//    private LinearLayout lldot;
//    private LinearLayout lleq;
//    private LinearLayout lliv;
//    private LinearLayout llmul;
//    private LinearLayout llsub;
//    private boolean mul;
//    private boolean sub;
//
//    TextView textView;
//    double val1;
//    double val2;
//
//    Vibrator vibrator;
//
//    public void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        requestWindowFeature(1);
//        setContentView(R.layout.calculator);
//        this.vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        this.fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
//        this.ll0 = findViewById(R.id.ll0);
//        this.ll1 = findViewById(R.id.ll1);
//        this.ll2 = findViewById(R.id.ll2);
//        this.ll3 = findViewById(R.id.ll3);
//        this.ll4 = findViewById(R.id.ll4);
//        this.ll5 = findViewById(R.id.ll5);
//        this.ll6 = findViewById(R.id.ll6);
//        this.ll7 = findViewById(R.id.ll7);
//        this.ll8 = findViewById(R.id.ll8);
//        this.ll9 = findViewById(R.id.ll9);
//        this.lldot = findViewById(R.id.lldot);
//        this.lleq = findViewById(R.id.lleq);
//        this.lladd = findViewById(R.id.lladd);
//        this.llsub = findViewById(R.id.llsub);
//        this.llmul = findViewById(R.id.llmul);
//        this.lldiv = findViewById(R.id.lldiv);
//        this.textView = findViewById(R.id.tv);
//        this.textView.setText("");
//        this.lliv = findViewById(R.id.lliv);
//        this.ll0.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "0";
//            textView.setText(sb);
//        });
//        this.ll1.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "1";
//            textView.setText(sb);
//        });
//        this.ll2.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "2";
//            textView.setText(sb);
//        });
//        this.ll3.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "3";
//            textView.setText(sb);
//        });
//        this.ll4.setOnClickListener(new OnClickListener() {
//            public void onClick(View view) {
//                Calculator.this.vibrator.vibrate(100);
//                TextView textView = Calculator.this.textView;
//                String sb = Calculator.this.textView.getText() +
//                        "4";
//                textView.setText(sb);
//            }
//        });
//        this.ll5.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "5";
//            textView.setText(sb);
//        });
//        this.ll6.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "6";
//            textView.setText(sb);
//        });
//        this.ll7.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "7";
//            textView.setText(sb);
//        });
//        this.ll8.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "8";
//            textView.setText(sb);
//        });
//        this.ll9.setOnClickListener(view -> {
//            Calculator.this.vibrator.vibrate(100);
//            TextView textView = Calculator.this.textView;
//            String sb = Calculator.this.textView.getText() +
//                    "9";
//            textView.setText(sb);
//        });
//        this.lldot.setOnClickListener(view -> {
//            String charSequence = Calculator.this.textView.getText().toString();
//            Calculator.this.dot = false;
//            if (charSequence.length() < 1) {
//                Calculator.this.vibrator.vibrate(100);
//                Calculator.this.textView.setText("0.");
//                return;
//            }
//            for (int i = 0; i < charSequence.length(); i++) {
//                if (charSequence.charAt(i) == '.') {
//                    Calculator.this.dot = true;
//                }
//            }
//            if (!Calculator.this.dot) {
//                Calculator.this.vibrator.vibrate(100);
//                TextView textView = Calculator.this.textView;
//                String sb = Calculator.this.textView.getText() +
//                        ".";
//                textView.setText(sb);
//            }
//        });
//        this.lladd.setOnClickListener(view -> {
//            String charSequence = Calculator.this.textView.getText().toString();
//            if (charSequence.length() >= 1) {
//                Calculator.this.vibrator.vibrate(100);
//                try {
//                    Calculator calculator = Calculator.this;
//                    String sb = Calculator.this.textView.getText() +
//                            "";
//                    calculator.val1 = Double.parseDouble(sb);
//                    Calculator.this.add = true;
//                    Calculator.this.textView.setText(null);
//                } catch (NumberFormatException ignored) {
//                }
//            }
//        });
//        this.llsub.setOnClickListener(view -> {
//            String charSequence = Calculator.this.textView.getText().toString();
//            if (charSequence.length() >= 1) {
//                Calculator.this.vibrator.vibrate(100);
//                try {
//                    Calculator calculator = Calculator.this;
//                    String sb = Calculator.this.textView.getText() +
//                            "";
//                    calculator.val1 = Double.parseDouble(sb);
//                    Calculator.this.sub = true;
//                    Calculator.this.textView.setText(null);
//                } catch (NumberFormatException ignored) {
//                }
//            }
//        });
//        this.llmul.setOnClickListener(view -> {
//            String charSequence = Calculator.this.textView.getText().toString();
//            if (charSequence.length() >= 1) {
//                Calculator.this.vibrator.vibrate(100);
//                try {
//                    Calculator calculator = Calculator.this;
//                    String sb = Calculator.this.textView.getText() +
//                            "";
//                    calculator.val1 = Double.parseDouble(sb);
//                    Calculator.this.mul = true;
//                    Calculator.this.textView.setText(null);
//                } catch (NumberFormatException ignored) {
//                }
//            }
//        });
//        this.lldiv.setOnClickListener(view -> {
//            String charSequence = Calculator.this.textView.getText().toString();
//            if (charSequence.length() >= 1) {
//                Calculator.this.vibrator.vibrate(100);
//                try {
//                    Calculator calculator = Calculator.this;
//                    String sb = Calculator.this.textView.getText() +
//                            "";
//                    calculator.val1 = Double.parseDouble(sb);
//                    Calculator.this.div = true;
//                    Calculator.this.textView.setText(null);
//                } catch (NumberFormatException ignored) {
//                }
//            }
//        });
//        this.lleq.setOnClickListener(view -> {
//            String str = "";
//            String charSequence = Calculator.this.textView.getText().toString();
//            if (charSequence.length() >= 1) {
//                Calculator.this.vibrator.vibrate(100);
//                try {
//                    Calculator calculator = Calculator.this;
//                    String sb = Calculator.this.textView.getText() +
//                            str;
//                    calculator.val2 = Double.parseDouble(sb);
//                } catch (NumberFormatException ignored) {
//                }
//                String str2 = ".0";
//                if (Calculator.this.add) {
//                    String valueOf = String.valueOf(Calculator.this.val1 + Calculator.this.val2);
//                    if ((valueOf.length() < 2 ? valueOf : valueOf.substring(valueOf.length() - 2)).contentEquals(str2)) {
//                        Calculator.this.textView.setText(valueOf.substring(0, valueOf.length() - 2));
//                    } else {
//                        TextView textView = Calculator.this.textView;
//                        String sb2 = (Calculator.this.val1 + Calculator.this.val2) +
//                                str;
//                        textView.setText(sb2);
//                    }
//                    Calculator.this.add = false;
//                }
//                if (Calculator.this.sub) {
//                    String valueOf2 = String.valueOf(Calculator.this.val1 - Calculator.this.val2);
//                    if ((valueOf2.length() < 2 ? valueOf2 : valueOf2.substring(valueOf2.length() - 2)).contentEquals(str2)) {
//                        Calculator.this.textView.setText(valueOf2.substring(0, valueOf2.length() - 2));
//                    } else {
//                        TextView textView2 = Calculator.this.textView;
//                        String sb3 = (Calculator.this.val1 - Calculator.this.val2) +
//                                str;
//                        textView2.setText(sb3);
//                    }
//                    Calculator.this.sub = false;
//                }
//                if (Calculator.this.mul) {
//                    String valueOf3 = String.valueOf(Calculator.this.val1 * Calculator.this.val2);
//                    if ((valueOf3.length() < 2 ? valueOf3 : valueOf3.substring(valueOf3.length() - 2)).contentEquals(str2)) {
//                        Calculator.this.textView.setText(valueOf3.substring(0, valueOf3.length() - 2));
//                    } else {
//                        TextView textView3 = Calculator.this.textView;
//                        String sb4 = Calculator.this.val1 * Calculator.this.val2 +
//                                str;
//                        textView3.setText(sb4);
//                    }
//                    Calculator.this.mul = false;
//                }
//                if (Calculator.this.div) {
//                    String valueOf4 = String.valueOf(Calculator.this.val1 / Calculator.this.val2);
//                    if ((valueOf4.length() < 2 ? valueOf4 : valueOf4.substring(valueOf4.length() - 2)).contentEquals(str2)) {
//                        Calculator.this.textView.setText(valueOf4.substring(0, valueOf4.length() - 2));
//                    } else {
//                        TextView textView4 = Calculator.this.textView;
//                        String sb5 = Calculator.this.val1 / Calculator.this.val2 +
//                                str;
//                        textView4.setText(sb5);
//                    }
//                    Calculator.this.div = false;
//                }
//            }
//        });
//        this.lliv.setOnClickListener(view -> {
//            String charSequence = Calculator.this.textView.getText().toString();
//            if (charSequence.length() >= 1) {
//                Calculator.this.vibrator.vibrate(100);
//                String charSequence2 = Calculator.this.textView.getText().toString();
//                if (charSequence2.length() != 0) {
//                    Calculator.this.textView.setText(charSequence2.substring(0, charSequence2.length() - 1));
//                }
//            }
//        });
//    }
//}
