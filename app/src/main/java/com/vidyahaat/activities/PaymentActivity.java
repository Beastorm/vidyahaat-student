package com.vidyahaat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.vidyahaat.R;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.constants.PaytmConstant;
import com.vidyahaat.model.paytmmodel.PaytmChecksum;
import com.vidyahaat.utilities.MyPreference;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    MyPreference myPreference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        progressBar = findViewById(R.id.progressBar);

        myPreference = new MyPreference(this);

        getToken(myPreference.readStudentId(), generateOrderId().substring(0, 28), Double.parseDouble(getIntent().getStringExtra("cost")));
    }

    private void getToken(final String customerId, final String orderId, final double totalAmount) {

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<PaytmChecksum> query = apiInterface.getToken(orderId, customerId,
                ((int) totalAmount) + ".00");

        query.enqueue(new Callback<PaytmChecksum>() {
            @Override
            public void onResponse(@NotNull Call<PaytmChecksum> call, @NotNull Response<PaytmChecksum> response) {

                progressBar.setVisibility(View.GONE);
                Log.i("PaymentActivity", "code: " + response.code());
                if (response.isSuccessful()) {

                    String token = Objects.requireNonNull(response.body()).getBody().getTxnToken();
                    progressBar.setVisibility(View.VISIBLE);
                    Log.i("PaymentActivity", "orderId: " + orderId);
                    Log.i("PaymentActivity", "PaytmConstant.mid: " + PaytmConstant.mid);
                    Log.i("PaymentActivity", "token: " + token);
                    Log.i("PaymentActivity", "amount: " + ((int) totalAmount) + ".00");
                    PaytmOrder paytmOrder = new PaytmOrder(orderId, PaytmConstant.mid, token, ((int) totalAmount) + ".00", PaytmConstant.callBackUrl + orderId);
                    TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
                        @Override
                        public void onTransactionResponse(@Nullable Bundle bundle) {
                            Toast.makeText(getApplicationContext(), "Payment Transaction response " + bundle.toString(), Toast.LENGTH_LONG).show();

                            if (bundle.getString("STATUS").equals("TXN_SUCCESS")) {

                                String orderId = bundle.getString("ORDERID");
                                String mid = bundle.getString("MID");
                                String txnId = bundle.getString("TXNID");
                                String amount = bundle.getString("TXNAMOUNT");
                                String currency = bundle.getString("CURRENCY");
                                String paymentMode = bundle.getString("PAYMENTMODE");
                                String txnDate = bundle.getString("TXNDATE");
                                String gatewayName = bundle.getString("GATEWAYNAME");
                                String bankTxnId = bundle.getString("BANKTXNID");
                                String bankName = bundle.getString("BANKNAME");
                                String checkSum = bundle.getString("CHECKSUMHASH");
                                String status = bundle.getString("STATUS");

                                createOrder(amount, orderId, paymentMode, txnId, gatewayName, txnDate, currency, mid, status, bankTxnId, bankName, checkSum);

                            }

                        }

                        @Override
                        public void networkNotAvailable() {
                            Toast.makeText(getApplicationContext(), "networkNotAvailable", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onErrorProceed(String s) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void clientAuthenticationFailed(String s) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void someUIErrorOccurred(String s) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onErrorLoadingWebPage(int i, String s, String s1) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onBackPressedCancelTransaction() {
                            finish();

                        }

                        @Override
                        public void onTransactionCancel(String s, Bundle bundle) {
                            finish();
                        }
                    });

                    transactionManager.setShowPaymentUrl("https://securegw-stage.paytm.in/theia/api/v1/showPaymentPage");
                    transactionManager.setAppInvokeEnabled(false);
                    transactionManager.startTransaction(PaymentActivity.this, 123);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PaytmChecksum> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(PaymentActivity.this, "Socket Time out. Please try again", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(PaymentActivity.this, "" + t.getCause(), Toast.LENGTH_SHORT).show();
                finish();


            }
        });
    }

    private void createOrder(String amount, String orderId, String paymentMode, String txnid,
                             String gateWayName, String txnDate, String currency, String mid, String status, String bankTxnId, String bankName, String checkSum) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);


        Call<ResponseBody> query = apiInterface.createOrder(getIntent().getStringExtra("id"), orderId,
                myPreference.readStudentId(), mid, amount, txnid, paymentMode, currency,
                gateWayName, bankTxnId, bankName, checkSum, status);

        query.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this, "Paid Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    // transaction error
                    Toast.makeText(PaymentActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {

            }
        });


    }

    // generating order id
    private String generateOrderId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && data != null) {
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }
    }

}