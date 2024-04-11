package com.marlon.gestion.productos.controlador;


import com.marlon.gestion.productos.entidades.Producto;
import com.marlon.gestion.productos.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @RequestMapping("/")
    public String verPaginaInicio(Model model , @Param("palabraClave") String palabraClave){


        List<Producto> listaProductos = productoServicio.listAll(palabraClave);


        model.addAttribute("listaProductos",listaProductos);
        model.addAttribute("palabraClave", palabraClave);

        return  "index";

    }


    @RequestMapping("/nuevo")
    public String mostrarFormularioDeRegistrarProducto( Model model){
        //lo que vsmos a pasar es una instancia de producto
        //poque una intancia: porque esta instancia sirve para indicarle que vamos
        //a guardar un nuevo objeto que se guardara en la bdd , este objeto en el formulario
        //lo vamos a enlazar con sus campos
        Producto producto = new Producto();
        model.addAttribute("producto" , producto);
        return "nuevo_producto";

    }


    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardarProducto(Producto producto){
        productoServicio.save(producto);
        return "redirect:/";
    }



    @RequestMapping("/editar/{id}")
    public ModelAndView mostrarFormularioDeEditarProducto(@PathVariable( name = "id") long id  ){

        Producto producto =  productoServicio.getProducto(id);

        ModelAndView modelo = new ModelAndView("editar_producto");
        modelo.addObject("producto",producto);

        return  modelo;



    }


    @RequestMapping("/eliminar/{id}")
    public String eliminarProducto( @PathVariable( name = "id") long id ){

        productoServicio.delete(id);
        return "redirect:/";

    }





}
