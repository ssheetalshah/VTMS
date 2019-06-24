package android.ics.com.vtms.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.ics.com.vtms.DetailsActivity;
import android.ics.com.vtms.JAVA_files.Vehicles;
import android.ics.com.vtms.R;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Vehichles_Adapter extends RecyclerView.Adapter<Vehichles_Adapter.ViewHolder>  {

    private static final String TAG = "ReviewsAdapter";
    private ArrayList<Vehicles> vehlist;
    public Context context;
    String resId = "";
    String finalStatus = "";
    private String prID;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name_of_company, code_of_consultant, city_of_consultant, go_btn, view_pro;
        LinearLayout re_layout;
        int pos;

        public ViewHolder(View view) {
            super(view);

            re_layout = (LinearLayout) view.findViewById(R.id.re_layout);
            name_of_company = (TextView) view.findViewById(R.id.name_of_company);
            city_of_consultant = (TextView) view.findViewById(R.id.city_of_consultant);
            code_of_consultant = (TextView) view.findViewById(R.id.code_of_consultant);
            go_btn = (TextView) view.findViewById(R.id.go_btn);
            view_pro = (TextView) view.findViewById(R.id.view_pro);
        }
    }

    public static Context mContext;

    public Vehichles_Adapter(Context mContext, ArrayList<Vehicles> Consult) {
        context = mContext;
        vehlist = Consult;

    }

    @Override
    public Vehichles_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_layout, parent, false);

        return new Vehichles_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Vehichles_Adapter.ViewHolder viewHolder, final int position) {
        Vehicles vehicles = vehlist.get(position);
        viewHolder.name_of_company.setText(vehicles.getVehicleDescription());
        //  viewHolder.email.setText(reviewsModel.getEmail());
        viewHolder.pos = position;

        prID = vehicles.getId();

        viewHolder.re_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicles vehicles = vehlist.get(position);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("Vehicles", vehicles);
                context.startActivity(intent);
//                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehlist.size();
    }

 /*   String Name_of_Company;
    String iD_of_cons;
    public static List<Vehicles> Filtered_Consult;
   // SessionManager sessionManager;
    Context context;
    Vehichles_Adapter adapter_consultant;
    //    private List<Consultants> moviesList;
    List<Vehicles> Consult ;
    private Dialog for_profile;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();
                if(charString.isEmpty()){
                    Filtered_Consult = Consult;

                }
                else{
                    Log.e("Query name ","is "+charString);
                    List<Vehicles> filtered = new ArrayList<>();
                    for(Vehicles row : Consult){
                        if( row.getname_of_company().toLowerCase().contains(charString.toLowerCase()) || row.getname_of_company().contains(charString.toUpperCase())){
                            Log.e("Consult name ","is "+row.getname_of_company());
//                            filtered.add(row);
                            filtered.add(row);

                        }
                    }
                    Filtered_Consult = filtered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = Filtered_Consult;

                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                Filtered_Consult = (List<Vehicles>)  results.values;
//                Consult.clear();
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_of_company, code_of_consultant, city_of_consultant,go_btn,view_pro;
        RelativeLayout re_layout;

        public MyViewHolder(View view) {

            super(view);
            re_layout = (RelativeLayout) view.findViewById(R.id.re_layout);
            name_of_company = (TextView) view.findViewById(R.id.name_of_company);
            city_of_consultant = (TextView) view.findViewById(R.id.city_of_consultant);
            code_of_consultant = (TextView) view.findViewById(R.id.code_of_consultant);
            go_btn = (TextView)view.findViewById(R.id.go_btn);
            view_pro = (TextView)view.findViewById(R.id.view_pro);
        }
    }

    public Vehichles_Adapter(List<Vehicles> moviesList) {
        this.Consult = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicles_xxx, parent, false);

        return new MyViewHolder(itemView);
    }
    public Boolean remove_consul_by_name(String name){
        List<Vehicles> filtered = new ArrayList<>();
        for(Vehicles row : Consult){
            if( row.getname_of_company().toString().contains(name)){
                Log.e("Consult name removal ","is "+row.getname_of_company());
//                            filtered.add(row);


            }else{
                Consult.remove(name);
                notifyDataSetChanged();
            }
        }
//       Filtered_Consult = filtered;
        return false;
    }

    public Boolean remove_consultants (int postion){
//          Consult.clear();
        Consult.remove(postion);
        notifyItemRemoved(postion);
        notifyDataSetChanged();
//          no_of_consult = myViewHolder.getAdapterPosition();
//          Consult.remove(no_of_consult);
//          myViewHolder.getLayoutPosition();
//          Consult.remove(no_of_consult);
        return true;

    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Vehicles Cons = Consult.get(position);

        holder.name_of_company.setText(Cons.getname_of_company());
        holder.city_of_consultant.setText(Cons.getcity_of_consultant());
        holder.code_of_consultant.setText(Cons.getcode_of_consultant());
        holder.re_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(v.getContext(), Cons.getname_of_company(), Toast.LENGTH_LONG).show();
                Log.e("Item is", "" + position);
                holder.go_btn.setVisibility(View.VISIBLE);
                holder.view_pro.setVisibility(View.VISIBLE);
                Name_of_Company = Cons.getname_of_company();
                iD_of_cons = Cons.getcode_of_consultant();

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("SID", iD_of_cons);
                context.startActivity(intent);

                // show_the_profile(v, Name_of_Company, iD_of_cons);
            }
        });
    }


    @Override
    public int getItemCount() {
        return Consult.size();
    }


    //
//    public Filter getFilter(){
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String charString = constraint.toString();
//                if(charString.isEmpty()){
//                    Filtered_Consult = Consult;
//
//                }
//                else{
//                    Log.e("Query name ","is "+charString);
//                    List<Consultants> filtered = new ArrayList<>();
//                    for(Consultants row : Consult){
//                        if(row.getname_of_company().toString().contains(charString)){
//                            Log.e("Consult name ","is "+row.getname_of_company());
////                            filtered.add(row);
//                            filtered.add(row);
//                        }
//                    }
//                    Filtered_Consult = filtered;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = Filtered_Consult;
//
//                return filterResults;
//            }
//
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                Filtered_Consult = (List<Consultants>)  results.values;
//                notifyDataSetChanged();
//            }
//        };
//
//    }
    public void updateList(List<Vehicles> list){
        Consult = list;
        notifyDataSetChanged();
    }
//    void filter(String text){
//        List<Consultants> temp = new ArrayList();
//        for(Consultants d: Consult){
//            //or use .equal(text) with you want equal match
//            //use .toLowerCase() for better matches
//            if(d.getname_of_company().contains(text)){
//                temp.add(d);
//            }
//        }
//        //update recyclerview
//        adapter_consultant.updateList(temp);
//    }
*/
}