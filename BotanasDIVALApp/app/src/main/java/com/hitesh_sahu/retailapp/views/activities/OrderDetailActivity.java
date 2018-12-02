package com.hitesh_sahu.retailapp.views.activities;

import android.app.Activity;
import android.app.Person;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.models.DataCenter;
import com.hitesh_sahu.retailapp.models.Money;
import com.hitesh_sahu.retailapp.models.OrderDetails;
import com.hitesh_sahu.retailapp.models.Orders;
import com.hitesh_sahu.retailapp.models.Products;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class OrderDetailActivity extends FragmentActivity {
    private EditText editTxtName, editTxtEmail, editTxtPhone, editTxtMess;
    private Button btnOrder;
    private int allInputs=0;
    private DatabaseReference mDatabaseRefRead, mDatabaseRefWrite;
    private String checkOutAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        btnOrder=findViewById(R.id.btnOrder);
        editTxtName=findViewById(R.id.inputName);
        editTxtEmail=findViewById(R.id.inputEmail);
        editTxtPhone=findViewById(R.id.inputPhone);
        editTxtMess=findViewById(R.id.inputMessage);

        checkOutAmount=getIntent().getExtras().getString(ECartHomeActivity.CHECKOUT_AMOUNT_KEY);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPurchaseDialog();
            }
        });

    }

    private boolean isEditTextEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void showPurchaseDialog() {

        allInputs = isEditTextEmpty(editTxtName) ? allInputs : allInputs+1;
        allInputs = isEditTextEmpty(editTxtEmail) ? allInputs : allInputs+1;
        allInputs = isEditTextEmpty(editTxtPhone) ? allInputs : allInputs+1;
        allInputs = isEditTextEmpty(editTxtMess) ? allInputs : allInputs+1;

        if (allInputs == 4) {
            AlertDialog.Builder exitScreenDialog = new AlertDialog.Builder(OrderDetailActivity.this, R.style.PauseDialog);

            exitScreenDialog.setTitle("CONFIRMAR ORDEN")
                    .setMessage("¿Te gustaría realizar este pedido?");
            exitScreenDialog.setCancelable(true);

            exitScreenDialog.setPositiveButton(
                    "Realizar Pedido",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //finish();
                            dialog.cancel();

                            final ArrayList<Orders> mListOrders=new ArrayList<>();
//                            mDatabaseRefRead=FirebaseDatabase.getInstance().getReference().child("Orders");
                            mDatabaseRefWrite=FirebaseDatabase.getInstance().getReference().child("Orders");


//                            mDatabaseRefRead.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    for(DataSnapshot child: dataSnapshot.getChildren()){
//                                        Orders mOrder=child.getValue(Orders.class);
//                                        mOrder.setOrderKey(child.getKey());
//                                        mListOrders.add(mOrder);
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
//                            int size=mListOrders.size();
//                            int mOrderKey;
//
//                            try{
//                                String key=mListOrders.get(size-1).getOrderKey();
//                                mOrderKey=Integer.parseInt(key)+1;
//                            } catch (Exception e){
//                                mOrderKey=19;
//                            }

                            String mName=editTxtName.getText().toString();
                            String mEMail=editTxtEmail.getText().toString();
                            String mPhone=editTxtPhone.getText().toString();
                            String mMess=editTxtMess.getText().toString();

                            Orders mOrder= new Orders();
                            mOrder.setClient(mName.concat(", ").concat(mEMail).concat(", ")
                                    .concat(mPhone).concat(", ").concat(mMess));
                            mOrder.setDate(Calendar.getInstance().getTime().toString());
                            mOrder.setTotal(checkOutAmount.substring(2));
                            mOrder.setStatus("Pendiente");

                            mDatabaseRefWrite.push().setValue(mOrder);

                            mDatabaseRefWrite=FirebaseDatabase.getInstance().getReference().child("OrderDetails");

                            OrderDetails mOrderDet=new OrderDetails();
                            List<Products> mListProd=DataCenter.getCenterRepository().getListOfProductsInShoppingList();

                            for(Products mProd: mListProd){
                                mOrderDet.setCodProduct(mProd.getCodProduct());
                                mOrderDet.setQuantity(mProd.getQuantity());
                                mOrderDet.setStatus("Pendiente");
                                mDatabaseRefWrite.push().setValue(mOrderDet);
                            }
                            //clear all list item
                            DataCenter.getCenterRepository().getListOfProductsInShoppingList().clear();

                            ECartHomeActivity.setItemCount(0);
                            ECartHomeActivity.getItemCountTextView().setText(String.valueOf(0));
                            ECartHomeActivity.setCheckoutAmount(new BigDecimal(BigInteger.ZERO));
                            ECartHomeActivity.getCheckOutAmount().setText(Money.getMoneyInstance(ECartHomeActivity.getCheckoutAmount()).toString());
                        }
                    });

            exitScreenDialog.setNegativeButton(
                    "Cancelar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            exitScreenDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (ECartHomeActivity.getItemCount() == 0) {
                        Snackbar.make(OrderDetailActivity.this.getWindow().getDecorView().findViewById(android.R.id.content)
                                , "¡Pedido Realizado Exitosamente!", Snackbar.LENGTH_LONG)
                                .setDuration(6000)
                                .show();

                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                finish();
                            }
                        }, 3000);
                    }

                }
            });

            AlertDialog alertMe = exitScreenDialog.create();
            alertMe.show();
        } else {
            Snackbar.make(OrderDetailActivity.this.getWindow().getDecorView().findViewById(android.R.id.content)
                    , "¡No has llenado todos los campos!", Snackbar.LENGTH_LONG)
                    .setDuration(5000)
                    .show();
        }

        allInputs=0;
    }
}
