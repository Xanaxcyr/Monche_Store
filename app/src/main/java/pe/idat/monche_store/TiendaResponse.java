package pe.idat.monche_store;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TiendaResponse {

    public static int Producto;
    @SerializedName("productoList")
    private List<Producto>  productoList; //lista de productos

    public List<Producto> getProductoList() {
        return productoList;
    }

    public static class Producto{
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private  String title;
        @SerializedName("price")
        private  Double price;
        @SerializedName("description")
        private  String description;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }
    }
}
