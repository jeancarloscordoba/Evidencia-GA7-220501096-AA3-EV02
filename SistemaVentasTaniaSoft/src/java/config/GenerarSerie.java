
package config;


public class GenerarSerie {
    
    public String NumeroSerie(int dato) {
        String numero="";
        int dto = dato + 1;
        
        if(dto<=10){
            numero = "0000" + dto;
        }else if(dto>10 && dto <=100){
            numero = "000" +dto;
        }else if(dto>100 && dto <=1000){
            numero = "00" + dto;
        }else if(dto>1000 && dto <=10000){
            numero = "0" + dto;
        }else if(dto>10000 && dto <=100000){
            numero = "" + dto;
        }
        return numero; 
    }
}
