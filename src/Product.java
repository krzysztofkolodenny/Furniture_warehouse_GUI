public class Product {
    Integer insert_id;
    String insert_nazwa;
    String insert_material;
    String insert_typ;
    String insert_cena;


    public Product(Integer insert_id,String insert_nazwa,String insert_material,String insert_typ,String insert_cena){
        this.insert_id=insert_id;
        this.insert_nazwa=insert_nazwa;
        this.insert_material=insert_material;
        this.insert_typ=insert_typ;
        this.insert_cena=insert_cena;

    }

    public Integer getInsertId(){
        return insert_id;

    }

    public String getInsertNazwa(){
        return insert_nazwa;

    }

    public String getInsertMaterial(){
        return insert_material;

    }

    public String getInsertTyp(){
        return insert_typ;

    }

    public String getInsertCena()
    {
        return insert_cena;
    }






}
