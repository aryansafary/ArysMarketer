package aryan.safary.sinoohe.data;

public class CityModel {
    int id , province_id;
    String name ,slug;

    public int getId() {
        return id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
