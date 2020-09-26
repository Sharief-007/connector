package com;



import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.file.Paths;
import java.sql.*;

@WebServlet("/SignerServlet")
@MultipartConfig
public class SignerServlet extends HttpServlet {
    int identity_number;
    String name,username,email,profession,gender,org,city,dob;
    String q1,q2,q3,q4,c1,c2,c3,c4;
    String home,state,country,phone;
    String facebook,twitter,linkedin,youtube,instagram,quora,overflow;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (req.getParameter("email") == null)
        {
            resp.sendRedirect("index.jsp");
        }
            personals(req,resp);
            education(req, resp);
            social(req, resp);
            address(req, resp);
            PersonalData prsnl = new PersonalData(email,name,username,gender,dob);
            Address addrs = new Address(home,state,country,phone);
            addrs.setProfession(profession);
            addrs.setOrg(org);
            addrs.setCity(city);
            Social social = new Social(facebook,twitter,linkedin,youtube,instagram,quora,overflow);
            Edu education = new Edu();
            education.set1(q1,c1);
            education.set2(q2,c2);
            education.set3(q3,c3);
            education.set4(q4,c4);

            Data data = new Data(addrs,social,education);
            if (req.getParameter("coverType").equals("video")){ data.setVideoAsCover(true); }
            User user = new User(prsnl,data);
            Upload_Files_to_Server(req,resp,user);
            try {
                user.setId(getId(email));
                user.setActive(true);
                addUserToDB(user);

//                JAXBContext contextObj = JAXBContext.newInstance(User.class);
//                Marshaller marshallerObj = contextObj.createMarshaller();
//                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//                marshallerObj.marshal(user,System.out);
                req.getSession().setAttribute("user",user);
                resp.sendRedirect("Home.jsp");
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void Upload_Files_to_Server(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException, ServletException {
        String path = Folder(req);
        Part profile = req.getPart("prf-pic");
        Part cover = req.getPart("cvr-pic");
        String p_name = "profile."+getExtension(Paths.get(profile.getSubmittedFileName()).getFileName().toString());
        String c_name = "cover."+getExtension(Paths.get(cover.getSubmittedFileName()).getFileName().toString());
        profile.write(path+File.separator+p_name);
        System.out.println("Saved at :::"+path+File.separator+p_name);
        cover.write(path+File.separator+c_name);
        user.set_pictures(p_name,c_name);
    }

    private String getExtension (String fileName)
    {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private String Folder(HttpServletRequest req) {
        String path = req.getServletContext().getRealPath("");
        File file = new File(path+File.separator+"images"+File.separator+req.getParameter("uname"));
        if (! file.exists())
        {
            file.mkdirs();
        }
        return file.getPath();
    }

    private void personals(HttpServletRequest req,HttpServletResponse res) {
        this.name = req.getParameter("Name");
        this.username = req.getParameter("uname");
        this.email = req.getParameter("email");
        this.profession = req.getParameter("profession");
        this.gender = req.getParameter("gender");
        this.org = req.getParameter("company");
        this.city = req.getParameter("city");
        this.dob = req.getParameter("dob");
    }
    private void education(HttpServletRequest req,HttpServletResponse res)
    {
        this.q1 = req.getParameter("q1");
        this.q2 = req.getParameter("q2");
        this.q3 = req.getParameter("q3");
        this.q4 = req.getParameter("q4");
        this.c1 = req.getParameter("c1");
        this.c2 = req.getParameter("c2");
        this.c3 = req.getParameter("c3");
        this.c4 = req.getParameter("c4");
    }
    private void address(HttpServletRequest req,HttpServletResponse res)
    {
        this.home = req.getParameter("homecity");
        this.state = req.getParameter("state");
        this.country = req.getParameter("country");
        this.phone = req.getParameter("phone");
    }

    private void social(HttpServletRequest req,HttpServletResponse res){
        this.facebook=req.getParameter("facebook");
        this.twitter=req.getParameter("twitter");
        this.linkedin=req.getParameter("linkedin");
        this.youtube=req.getParameter("youtube");
        this.quora = req.getParameter("quora");
        this.overflow=req.getParameter("stackoverflow");
        this.instagram=req.getParameter("instagram");
    }
    private void addUserToDB(User user) throws Exception {
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("insert into user values(?,?,?,?,?,?,?,?,?,?)");
        stmt.setInt(1,user.getId());
        stmt.setString(2,user.getPersonalData().getName());
        stmt.setString(3,user.getPersonalData().getUsername());
        stmt.setString(4,user.getPersonalData().getDob());
        stmt.setString(5,user.getPersonalData().getGender());
        stmt.setObject(6,user.getFriends());
        stmt.setObject(7,user.getData());
        stmt.setObject(8,user.getRequests());
        stmt.setObject(9,user.getNotifications());
        stmt.setBoolean(10,user.isActive());

        System.out.println(stmt.execute());

        Statement statement = conn.createStatement();
        statement.execute("insert into pictures (id,profile,cover) values ("+user.getId()+",'"+user.getPrf_picture()+"','"+user.getCvr_picture()+"')");
    }


    public int getId(String mail) throws Exception {
        Connection conn = new DatabaseManager().getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select id from credentials where gmail = '"+mail+"'");
        rs.next();
        return Integer.parseInt(rs.getString(1));
    }
}
