package com.example.paymentgateway;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCShipmentInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

public class MainActivity extends AppCompatActivity implements SSLCTransactionResponseListener {

    private TextView test_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test_one = findViewById(R.id.sucess_text);


        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("test5fb634edbdf9f","test5fb634edbdf9f@ssl",10,
                SSLCCurrencyType.BDT,"123456789098765","yourProductType",SSLCSdkType.TESTBOX);
        final SSLCCustomerInfoInitializer customerInfoInitializer = new SSLCCustomerInfoInitializer("customer name", "customer email",
                "address", "dhaka", "1214", "Bangladesh","phoneNumber");
        final SSLCProductInitializer productInitializer = new SSLCProductInitializer ("food", "food",
                new SSLCProductInitializer.ProductProfile.TravelVertical("Travel", "10",
                        "A", "12", "Dhk-Syl"));
        final SSLCShipmentInfoInitializer shipmentInfoInitializer = new SSLCShipmentInfoInitializer ("Courier",
                2, new SSLCShipmentInfoInitializer.ShipmentDetails("AA","Address 1",
                "Dhaka","1000","BD"));

        IntegrateSSLCommerz
                .getInstance(MainActivity.this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(customerInfoInitializer)
                .addProductInitializer(productInitializer)
                .buildApiCall(this);
    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {
            test_one.setText(sslcTransactionInfoModel.getStatus()+"---"+sslcTransactionInfoModel.getAPIConnect());
    }


    @Override
    public void transactionFail(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void merchantValidationError(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}