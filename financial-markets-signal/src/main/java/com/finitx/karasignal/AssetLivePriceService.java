package com.finitx.karasignal;

//import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AssetLivePriceService {
    
//    private static assetsLiveDataReaderFromBinance asstesLiveDataReaderFromBinance;

    public static ConfigurableApplicationContext context;
    
    public static void main(String[] args) {
        context = SpringApplication.run(AssetLivePriceService.class, args);
        
//        ArrayList<String> streams = new ArrayList<>();
//        streams.add("btcusdt@trade");
//        streams.add("bnbusdt@trade");

        
        
//        asstesLiveDataReaderFromBinance = context.getBean(assetsLiveDataReaderFromBinance.class);
//        
//        asstesLiveDataReaderFromBinance.addSymbole("ethbust");
//        asstesLiveDataReaderFromBinance.addSymbole("ltcbusd");
//        asstesLiveDataReaderFromBinance.addSymbole("trxbusd");
//        asstesLiveDataReaderFromBinance.addSymbole("xrpbusd");
//        asstesLiveDataReaderFromBinance.addSymbole("btcusdt");
//        asstesLiveDataReaderFromBinance.addSymbole("bnbusdt");
//        asstesLiveDataReaderFromBinance.getData();
        
//        asstesLiveDataReaderFromBinance.getData(streams);
    }

}
