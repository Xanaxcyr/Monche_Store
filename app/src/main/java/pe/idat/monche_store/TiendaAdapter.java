package pe.idat.monche_store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.ProductoViewHolder> {

    private final List<TiendaResponse.Producto> productos;

    public TiendaAdapter(List<TiendaResponse.Producto> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ProductoViewHolder(layoutInflater.inflate(R.layout.item_producto, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        TiendaResponse.Producto item = productos.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static final class ProductoViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo,price,description;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tituloitem);
            price = itemView.findViewById(R.id.precio);
            description= itemView.findViewById(R.id.descripcion);
        }

        public void bind(TiendaResponse.Producto producto) {
            titulo.setText(producto.getTitle());
            String precio = "S/ " + producto.getPrice().toString();
            price.setText(precio);
            description.setText(producto.getDescription());

        }
    }
}
