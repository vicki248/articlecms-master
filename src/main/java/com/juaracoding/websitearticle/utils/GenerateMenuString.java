package com.juaracoding.websitearticle.utils;

import com.juaracoding.websitearticle.model.Akses;
import com.juaracoding.websitearticle.model.Menu;
import com.juaracoding.websitearticle.model.MenuHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateMenuString {


    private StringBuilder sBuild = new StringBuilder();
    private List<MenuHeader> listMenuHeader = new ArrayList<MenuHeader>();

        public String menuInnerHtml(Akses akses)
    {
        Map<String,String> mapx = new HashMap<String,String>();
        List<Menu> listMenuz = akses.getListMenuAkses();
        String namaMenuHeader = "";
        String linkMenu = "";
        String strListMenuHtml = "";
        String [] strLinkArr = null;
        String [] strSplitLink= null;

        for (int i=0;i<listMenuz.size();i++)
        {
            namaMenuHeader = listMenuz.get(i).getMenuHeader().getNamaMenuHeader();
            sBuild.setLength(0);
            linkMenu = sBuild.append(listMenuz.get(i).getPathMenu()).append("-").
                    append(listMenuz.get(i).getNamaMenu()).toString();//index 0  path menu, 1 nama menu

            if(mapx.get(namaMenuHeader)==null)
            {
                mapx.put(namaMenuHeader,linkMenu);
            }
            else
            {
                sBuild.setLength(0);
                linkMenu = sBuild.append(mapx.get(namaMenuHeader)).append("#").append(linkMenu).toString();
                mapx.put(namaMenuHeader,linkMenu);
            }
        }

        for (Map.Entry<String,String> strMap:
             mapx.entrySet()) {
            sBuild.setLength(0);
            strListMenuHtml = sBuild.append(strListMenuHtml).append("<li>")
                    .append("<a href=\"\">").append(strMap.getKey()).append("</a>")
                    .append("<ul class=\"menu-has-children\">").toString();
            linkMenu = strMap.getValue();
//            linkMenu = linkMenu.substring(0,linkMenu.length()-1);//menghilangkan tanda # di akhir
            strLinkArr = linkMenu.split("#");
            for (int i=0;i<strLinkArr.length;i++)
            {
                strSplitLink = strLinkArr[i].split("-");
                sBuild.setLength(0);
                strListMenuHtml = sBuild.append(strListMenuHtml).
                        append("<li>").append("<a href=\"").append(strSplitLink[0]).append("\">")
                        .append(strSplitLink[1]).append("</a>").append("</li>").toString();
            }
            sBuild.setLength(0);
            strListMenuHtml = sBuild.append(strListMenuHtml)
                    .append("</ul>").append("</li>").toString();
        }
//        System.out.println("STRING HTML : \n "+strListMenuHtml);
        return strListMenuHtml;
    }
}

//    String x="<li >\n" +
//            "    <a href=\"#\">SALES</a>\n" +
//            "<ul class=\"menu-dropdown\">\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/salesone\">/api/menu/salesone</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/salestwo\">/api/menu/salestwo</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/salesthree\">/api/menu/salesthree</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/hrone\">/api/menu/hrone</a>\n" +
//            "        </li>\n" +
//            "</ul>\n" +
//            "</li>\n" +
//            "<li>\n" +
//            "    <a href=\"#\">HRD</a>\n" +
//            "<ul class=\"menu-dropdown\">\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/hrone\">/api/menu/hrone</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/hrtwo\">/api/menu/hrtwo</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/hrthree\">/api/menu/hrthree</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/financeone\">/api/menu/financeone</a>\n" +
//            "        </li>\n" +
//            "</ul>\n" +
//            "</li>\n" +
//            "<li>\n" +
//            "    <a href=\"#\">FINANCE</a>\n" +
//            "<ul class=\"menu-dropdown\">\n" +
//            "\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/financeone\">/api/menu/financeone</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/financetwo\">/api/menu/financetwo</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/financethree\">/api/menu/financethree</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/usrone\">/api/menu/usrone</a>\n" +
//            "        </li>\n" +
//            "\t</ul>\n" +
//            "</li>\n" +
//            "\n" +
//            "<li>\n" +
//            "    <a href=\"#\">USER MANAGEMENT</a>\n" +
//            "<ul class=\"menu-dropdown\">\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/usrone\">/api/menu/usrone</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/usrtwo\">/api/menu/usrtwo</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/usrthree\">/api/menu/usrthree</a>\n" +
//            "        </li>\n" +
//            "        <li>\n" +
//            "            <a href=\"/api/menu/usrfour\">/api/menu/usrfour</a>\n" +
//            "        </li>\n" +
//            "</ul>\n" +
//            "</li>";