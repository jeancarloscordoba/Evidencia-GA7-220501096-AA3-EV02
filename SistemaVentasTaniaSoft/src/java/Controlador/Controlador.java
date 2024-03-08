package Controlador;

import Modelo.Cliente;
import Modelo.ClienteDAO;
import Modelo.Empleado;
import Modelo.EmpleadoDAO;
import Modelo.Producto;
import Modelo.ProductoDAO;
import Modelo.Venta;
import Modelo.VentaDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    Empleado em = new Empleado();
    EmpleadoDAO edao = new EmpleadoDAO();
    Cliente cl = new Cliente();
    ClienteDAO cdao = new ClienteDAO();
    Producto pr = new Producto();
    ProductoDAO pdao = new ProductoDAO();
    int ide;
    int idc;
    int idp;

    Venta ve = new Venta();
    List<Venta> lista = new ArrayList<>();
    int item;
    int cod;        //El codigo de la venta es el id de la venta (IdVentas en la base de datos). 
    String descripcion;
    int precio;
    int cant;
    int subtotal;
    int totalPagar;

    String numeroserie = "";
    VentaDAO vdao = new VentaDAO();

    public Controlador() {
        PersistanceController.makeEntitiMF();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");

        /*if (menu.equals("Principal")) {
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        }*/
        if (menu != null && menu.equals("Principal")) {
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        }

        //MENU EMPLEADOS
        if (menu.equals("Empleado")) {
            switch (accion) {

                case "Listar":
                    //List lista = edao.listar(); 
                    List lista = edao.getListaEmpleados();

                    request.setAttribute("empleados", lista); //Aquí se esta definiendo empleados como un atributo del arrays lista. 
                    break;

                case "Agregar":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String tel = request.getParameter("txtTel");
                    String est = request.getParameter("txtEstado");
                    String user = request.getParameter("txtUser");

                    em.setDni(dni);
                    em.setNom(nom);
                    em.setTel(tel);
                    em.setEstado(est);
                    em.setUser(user);

                    //edao.agregar(em);
                    edao.guardarEmpleado(em);

                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    ide = Integer.parseInt(request.getParameter("id"));

                    //Empleado e = edao.listarId(ide); 
                    Empleado e = edao.getEditarEmpleado(ide);

                    request.setAttribute("empleado", e);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Actualizar":
                    String dni1 = request.getParameter("txtDni");
                    String nom1 = request.getParameter("txtNombres");
                    String tel1 = request.getParameter("txtTel");
                    String est1 = request.getParameter("txtEstado");
                    String user1 = request.getParameter("txtUser");

                    em.setDni(dni1);
                    em.setNom(nom1);
                    em.setTel(tel1);
                    em.setEstado(est1);
                    em.setUser(user1);
                    em.setId(ide);

                    System.out.println("EMPLEADO ACTUALIZADO: " + em);

                    //edao.actualizar(em);
                    edao.actualizarEmpleado(em);

                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Delete":
                    ide = Integer.parseInt(request.getParameter("id"));

                    //edao.delete(ide);
                    edao.eliminarEmpleado(ide);

                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Empleado.jsp").forward(request, response);
        }

        //MENU CLIENTES
        if (menu.equals("Cliente")) {
            switch (accion) {
                //Acción de Listar para listar la lista de clientes
                case "Listar":
                    List lista = cdao.getListaClientes();//cdao.listar();

                    request.setAttribute("clientes", lista); //Aquí se esta definiendo clientes como un atributo del arrays lista. 
                    break;

                //Acción del boton Editar para editar un Cliente
                case "Editar":
                    // Se le asigan el parametro de id (IdCliente) a la variable "idc"
                    idc = Integer.parseInt(request.getParameter("id"));

                    // Se le asigna la acción del método "getEditarCliente" a la variable c de tipo "Cliente"
                    Cliente c = cdao.getEditarCliente(idc);//cdao.listarId(idc);

                    // Se establece el parámetro editarCliente en true para indicar que se está editando un cliente
                    request.setAttribute("editarCliente", true);

                    // Se envían los datos del cliente al JSP
                    request.setAttribute("cliente", c);

                    // Se recarga la página con la acción de "Listar".
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                // Acción del botón Guardar para guardar un Cliente
                case "Guardar":
                    // Se encapsulan en variables los datos del cliente agregados en los name de cada dato.
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String tel = request.getParameter("txtTel");
                    String dir = request.getParameter("txtDir");
                    String est = request.getParameter("txtEstado");

                    // Se le asigan al objeto cl (Cliente) los datos ingresados por el usuario
                    cl.setDni(dni);
                    cl.setNom(nom);
                    cl.setTel(tel);
                    cl.setDir(dir);
                    cl.setEstado(est);

                    // Validar si hay campos vacíos antes de guardar.
                    if (dni.isEmpty() || nom.isEmpty() || tel.isEmpty() || dir.isEmpty() || est.isEmpty()) {
                        // Si hay campos vacíos, mostrar un mensaje de error al usuario.
                        request.setAttribute("mensajeError", "ERROR, Se deben diligenciar todos los datos del Cliente para guardarlo.");

                        // Se guarda el objeto cl (Cliente) en el atributo "cliente" para mantener los datos ingreados en el formulario si hay un error
                        request.setAttribute("cliente", cl);

                        // Se recarga la página con la acción de "Listar".
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return;
                    }

                    // Validar el DNI ingresado
                    if (dni.length() > 10) {
                        // Si el DNI tiene más de 10 dígitos, mostrar un mensaje de error en el campo de DNI
                        request.setAttribute("mensajeErrorDni", "ERROR en el DNI. (menor a 10 digitos) ");

                        // Mensaje de error en la interfaz por DNI erróneo
                        request.setAttribute("mensajeError", "DNI erróneo. Por favor ingrese un DNI válido.");

                        // Se guarda el objeto cl (Cliente) en el atributo "cliente" para mantener los datos ingreados en el formulario si hay un error
                        request.setAttribute("cliente", cl);

                        // Se recarga la página con la acción de "Listar".
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return;
                    }

                    // Validar el teléfono ingresado
                    if (tel.length() > 15) {
                        // Mensaje error para el campo de teléfono
                        request.setAttribute("mensajeErrorTelefono", "Hay un ERROR en el Teléfono. (menor a 15 digitos)");

                        // Mensaje de error en la interfaz por teléfono erróneo
                        request.setAttribute("mensajeError", "Teléfono erróneo. Por favor ingrese un teléfono válido.");

                        // Se guarda el objeto cl (Cliente) en el atributo "cliente" para mantener los datos ingreados en el formulario si hay un error
                        request.setAttribute("cliente", cl);

                        // Se recarga la página con la acción de "Listar".
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return;
                    }

                    // Validar el estado ingresado
                    if (!est.equals("1") && !est.equals("2")) {
                        // Si el estado no es 1 ni 2, se muestra un mensaje de error en el campo de estado
                        request.setAttribute("mensajeErrorEstado", "Hay un ERROR en el Estado");

                        // Mensaje de error en la interfaz por Estado erróneo
                        request.setAttribute("mensajeError", "El estado debe ser 1 (Cliente activo) o 2 (Cliente inactivo).");

                        // Se guarda el objeto cl (Cliente) en el atributo "cliente" para mantener los datos ingreados en el formulario si hay un error
                        request.setAttribute("cliente", cl);

                        // Se recarga la página con la acción de "Listar".
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return;
                    }

                    // Si no hay errores, se procede a guardar el cliente
                    cdao.guardarCliente(cl);

                    // Si se guarda el Cliente de forma correcta se envía un mensaje de éxito.
                    request.setAttribute("mensajeCorrecto", "¡Se ha guardado el Cliente con éxito!");

                    // Se recarga la página con la acción de "Listar".
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                case "cancelarGuardado":
                    // Se atrapan los campos del formulario en varibales
                    String dni3 = request.getParameter("txtDni");
                    String nom3 = request.getParameter("txtNombres");
                    String tel3 = request.getParameter("txtTel");
                    String dir3 = request.getParameter("txtDir");
                    String est3 = request.getParameter("txtEstado");

                    // Se verifica si los campos del formulario están vacíos
                    if (dni3.isEmpty() && nom3.isEmpty() && tel3.isEmpty() && dir3.isEmpty() && est3.isEmpty()) {
                        //En caso que los campos esten vacios de arroja un mensaje
                        request.setAttribute("mensajeCancelar", "¡Inicia a llenar los datos del cliente para cancelar el guardado!");
                    } else {
                        // Se crea una varibale para que almacene una lista vacia
                        List<Cliente> clienteVacioParaGuardado = new ArrayList<>();
                        
                        // Se le asigna la lista vacía al atributo "clientes".
                        request.setAttribute("clientes", clienteVacioParaGuardado);

                        // Mensaje de exito al cancelar una edición 
                        request.setAttribute("mensajeCancelar", "¡Se cancelo el guardado del cliente!");
                    }

                    // Se recarga la pagina con la acción de "Listar".
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                //Acción del boton Actualizar para actualizar un Cliente
                case "Actualizar":
                    String dni1 = request.getParameter("txtDni");
                    String nom1 = request.getParameter("txtNombres");
                    String tel1 = request.getParameter("txtTel");
                    String dir1 = request.getParameter("txtDir");
                    String est1 = request.getParameter("txtEstado");

                    // Se le asigan al objeto cl (Cliente) los datos ingresados por el usuario
                    cl.setDni(dni1);
                    cl.setNom(nom1);
                    cl.setTel(tel1);
                    cl.setDir(dir1);
                    cl.setEstado(est1);
                    cl.setId(idc);

                    // Validar si hay campos vacíos.
                    if (dni1.isEmpty() || nom1.isEmpty() || tel1.isEmpty() || dir1.isEmpty() || est1.isEmpty()) {
                        // Si hay campos vacios, se muestra un mensaje de error al usuario.
                        request.setAttribute("mensajeError", "ERROR, Se debe llenar todos los campos para actualizar un cliente.");

                        // Se guarda el objeto cl (Cliente) en el atributo "cliente" para mantener los datos ingreados en el formulario si hay un error
                        request.setAttribute("cliente", cl);

                        //Se indica que se esta editando aun un cliente con el parametro editarCliente en true. (Evitar terminar la edición por un error)
                        request.setAttribute("editarCliente", true);

                        // Se recarga la pagina con la acción de "Listar".
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return;
                    }

                    // Validar el DNI ingresado
                    if (dni1.length() > 10) {
                        // Si el DNI tiene más de 10 dígitos, mostrar un mensaje de error en el campo de DNI
                        request.setAttribute("mensajeErrorDni", "ERROR en el DNI. (menor a 10 digitos)");

                        // Mensaje de error en la interfaz por DNI erroneo
                        request.setAttribute("mensajeError", "DNI erróneo. Por favor actualize el Dni a con un Dni valido.");

                        // Se guarda el objeto cl (Cliente) en el atributo "cliente" para mantener los datos ingreados en el formulario si hay un error
                        request.setAttribute("cliente", cl);

                        //Se indica que se esta editando aun un cliente con el parametro editarCliente en true. (Evitar terminar la edición por un error)
                        request.setAttribute("editarCliente", true);

                        // Se recarga la pagina con la acción de "Listar".
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return;
                    }

                    // Validar el telefono ingresado
                    if (tel1.length() > 15) {
                        // Mensaje error para el campo de teléfono
                        request.setAttribute("mensajeErrorTelefono", "Hay un ERROR en el Teléfono");

                        // Mensaje de error en la interfaz por teléfono erroneo
                        request.setAttribute("mensajeError", "Telefono erróneo. Por favor actualize el Telefono a con un Telefono valido.");

                        // Se guarda el objeto cl (Cliente) en el atributo "cliente" para mantener los datos ingreados en el formulario si hay un error
                        request.setAttribute("cliente", cl);

                        //Se indica que se esta editando aun un cliente con el parametro editarCliente en true. (Evitar terminar la edición por un error)
                        request.setAttribute("editarCliente", true);

                        // Se recarga la pagina con la acción de "Listar".
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return;
                    }

                    // Validar el estado ingresado
                    if (!est1.equals("1") && !est1.equals("2")) {
                        // Si el estado no es 1 ni 2, se muestra un mensaje de error en el campo de estado
                        request.setAttribute("mensajeErrorEstado", "Hay un ERROR en el Estado");

                        // Mensaje de error en la interfaz por Estado erróneo
                        request.setAttribute("mensajeError", "El estado debe ser 1 (Cliente activo) o 2 (para Cliente inactivo).");

                        // Se guarda el objeto cl (Cliente) en el atributo "cliente" para mantener los datos ingreados en el formulario si hay un error
                        request.setAttribute("cliente", cl);

                        //Se indica que se esta editando aun un cliente con el parametro editarCliente en true. (Evitar terminar la edición por un error)
                        request.setAttribute("editarCliente", true);

                        // Se recarga la pagina con la acción de "Listar".
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return;
                    }

                    // Si no hay errores, se procede a actualizar el cliente
                    if (request.getAttribute("mensajeError") == null) {
                        //Se actualiza el cliente con el metodo actualizarCliente
                        cdao.actualizarCliente(cl);

                        // Se ejecuta el mensaje exito 
                        request.setAttribute("mensajeCorrecto", "¡Se ha actualizado el Cliente con éxito!");

                        // Actualización exitosa, redireccionar a la página de listado de clientes
                        request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                        return; //Se retorna para salir del metodo de actualizar 
                    }

                    // Se recarga la pagina con la acción de "Listar".
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                //Acción de Cancelar para el boton de cancelar edición. 
                case "cancelarEdicion":
                    // Se crea una varibale para que almacene una lista vacia
                    List<Cliente> clienteVacioParaEdicion = new ArrayList<>();

                    // Se le asigna la lista vacía al atributo "clientes".
                    request.setAttribute("clientes", clienteVacioParaEdicion);

                    // Mensaje de exito al cancelar una edición 
                    request.setAttribute("mensajeCancelar", "¡Se cancelo la edicion del cliente!");

                    // Se recarga la pagina con la acción de "Listar".
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                //Acción del boton Eliminar para eliminar un Cliente
                case "Delete":
                    // Se le asigna el parámetro id (IdCliente) a la variable "idc".
                    idc = Integer.parseInt(request.getParameter("id"));

                    // Se intenta eliminar el cliente y se verifica si se realizó con éxito.
                    if (cdao.eliminarCliente(idc)) {
                        // Si se elimina correctamente, se muestra un mensaje de éxito.
                        request.setAttribute("mensajeCorrecto", "¡Se ha eliminado el Cliente con éxito!");
                    } else {
                        // Si hay algún error al eliminar, se muestra un mensaje de error.
                        request.setAttribute("mensajeError", "ERROR, no es posible eliminar el Cliente");
                    }

                    // Se recarga la página con la acción de "Listar".
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Clientes.jsp").forward(request, response);
        }

        //MENU PRODUCTOS
        if (menu.equals(
                "Producto")) {
            switch (accion) {
                case "Listar":
                    //List lista = pdao.listar();
                    List lista = pdao.getListaProductos();

                    request.setAttribute("productos", lista); //Aquí se esta definiendo productos como un atributo del arrays lista.
                    break;

                case "Agregar":
                    String nom = request.getParameter("txtNombres");
                    Integer pre = Integer.parseInt(request.getParameter("txtPrecio"));
                    Integer sto = Integer.parseInt(request.getParameter("txtStock"));
                    String des = request.getParameter("txtDescripcion");
                    String est = request.getParameter("txtEstado");

                    pr.setNom(nom);
                    pr.setPrecio(pre);
                    pr.setStock(sto);
                    pr.setDescripcion(des);
                    pr.setEstado(est);

                    //pdao.agregar(pr);
                    pdao.guardarProducto(pr);

                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    idp = Integer.parseInt(request.getParameter("id"));

                    //Producto p = pdao.listarId(idp);
                    Producto p = pdao.getProductoPorId(idp);

                    request.setAttribute("producto", p);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Actualizar":
                    String nom1 = request.getParameter("txtNombres");
                    String pre1 = request.getParameter("txtPrecio");
                    String sto1 = request.getParameter("txtStock");
                    String des1 = request.getParameter("txtDescripcion");
                    String est1 = request.getParameter("txtEstado");

                    Integer precio1 = Integer.parseInt(pre1);
                    Integer stock1 = Integer.parseInt(sto1);

                    pr.setNom(nom1);
                    pr.setPrecio(precio1);
                    pr.setStock(stock1);
                    pr.setDescripcion(des1);
                    pr.setEstado(est1);
                    pr.setId(idp);
                    pdao.actualizar(pr);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Delete":
                    idp = Integer.parseInt(request.getParameter("id"));
                    pdao.delete(idp);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Producto.jsp").forward(request, response);
        }

        //MENU NUEVA VENTA
        if (menu.equals(
                "NuevaVenta")) { //Este falta por codificar a JPA

            ve = new Venta();
            //numeroserie
            request.setAttribute("nserie", numeroserie);

            switch (accion) {
                //Este es el boton buscar, para buscar cliente en nueva venta. 
                case "BuscarCliente":
                    String dni = request.getParameter("DNIcliente");

                    cl.setDni(dni);
                    //cl = cdao.buscar(dni);

                    request.setAttribute("cl", cl);
                    request.setAttribute("nserie", numeroserie);
                    break;

                //Este es el boton buscar, para buscar producto en nueva venta.  
                case "BuscarProducto":
                    int id = Integer.parseInt(request.getParameter("IDproducto"));
                    pr = pdao.listarId(id);

                    request.setAttribute("cl", cl);
                    request.setAttribute("producto", pr);
                    request.setAttribute("lista", lista);
                    request.setAttribute("totalpagar", totalPagar);
                    request.setAttribute("nserie", numeroserie);
                    //System.out.println("PRID: "+pr.getId()+"___");
                    break;

                case "AgregarProducto":
                    request.setAttribute("nserie", numeroserie);
                    request.setAttribute("cl", cl);

                    totalPagar = 0;
                    item = item + 1;
                    cod = pr.getId(); //HAY UN ERROR AQUI, YA QUE NO ESTA MANDANDO EL ID DEL PRODUCTO SINO UN 0. 
                    descripcion = request.getParameter("nomproducto");
                    precio = Integer.parseInt(request.getParameter("precio"));
                    cant = Integer.parseInt(request.getParameter("cant"));
                    subtotal = precio * cant;

                    ve = new Venta();

                    ve.setItem(item);
                    ve.setIdproducto(cod);
                    ve.setDescripcionP(descripcion);
                    ve.setPrecio(precio);
                    ve.setCantidad(cant);
                    ve.setSubtotal(subtotal);

                    lista.add(ve);

                    for (int i = 0; i < lista.size(); i++) {
                        totalPagar = totalPagar + lista.get(i).getSubtotal();
                    }

                    request.setAttribute("totalpagar", totalPagar);
                    request.setAttribute("lista", lista);
                    break;

                case "eliminarproducto":
                    request.setAttribute("nserie", numeroserie);
                    request.setAttribute("cl", cl);
                    cod = pr.getId();
                    System.out.println("LLEGO AL ELIMINAR PRODUCOTO");

                    totalPagar = 0;
                    int idprod = Integer.parseInt(request.getParameter("id"));
                    System.out.println("EL ID DEL PRODUCTO: " + idprod);

                    for (int i = 0; i < lista.size(); i++) {

                        if (idprod == lista.get(i).getIdproducto()) {
                            System.out.println("ENTRO EN LA ELIMINACION");

                            System.out.println("ELEMENTO ELIMINADO: " + lista.remove(i));
                        } else {
                            totalPagar = totalPagar + lista.get(i).getSubtotal();
                        }

                    }
                    request.setAttribute("totalpagar", totalPagar);
                    request.setAttribute("lista", lista);

                    break;

                case "GenerarVenta":
                    //request.setAttribute("nserie", numeroserie);
                    System.out.println("NUMERO DE SERIE: " + numeroserie);

                    for (int i = 0; i < lista.size(); i++) {
                        Producto p = new Producto();

                        int cantidad = lista.get(i).getCantidad();
                        int idproducto = lista.get(i).getIdproducto();

                        ProductoDAO aO = new ProductoDAO();
                        p = aO.buscar(idproducto);

                        int stockActual = p.getStock() - cantidad;

                        aO.actualizarstock(idproducto, stockActual);
                    }

                    //Guardar Venta
                    ve.setIdcliente((int) cl.getId());
                    ve.setIdempleado(2);
                    ve.setNumserie(numeroserie);
                    ve.setFecha("2019-06-14");
                    System.out.println("EL TOTAL A pAGAR Es: " + totalPagar);
                    ve.setMonto(totalPagar);
                    ve.setEstado("1");

                    int resp = vdao.guardarVenta(ve);
                    System.out.println("EL RESULTADO: " + resp + "___");

                    //Guardar detalle ventas
                    System.out.println("ID DETALLE VENTA: " + vdao.IdVentas());

                    int idv = Integer.parseInt(vdao.IdVentas());

                    for (int i = 0; i < lista.size(); i++) {
                        ve = new Venta();

                        ve.setId(idv);
                        ve.setIdproducto(lista.get(i).getIdproducto());
                        ve.setCantidad(lista.get(i).getCantidad());
                        ve.setPrecio(lista.get(i).getPrecio());

                        vdao.GuardarDetalleVentas(ve);
                    }

                    numeroserie = vdao.getConsecutivoFactura(); //La variable "numeroserie" almacena el maximo numero de serie que existe en la base datos. 
                    request.setAttribute("nserie", numeroserie);

                    lista.clear();

                    break;

                case "Cancelar":
                    lista.clear();
                    totalPagar = 0;
                    request.setAttribute("lista", lista);
                    request.setAttribute("totalpagar", totalPagar);
                    request.setAttribute("nserie", numeroserie);
                    break;

                default: //Hay un error en el numero de serie que esta provocando que no se incremente al generar una venta. (Posiblemente tambien lo este afectando el contenido de la clase GenerarSerie)
                    ve = new Venta();
                    lista = new ArrayList<>();
                    item = 0;
                    totalPagar = 0;

                    numeroserie = vdao.getConsecutivoFactura(); //La variable "numeroserie" almacena el maximo numero de serie que existe en la base datos. 
                    request.setAttribute("nserie", numeroserie);

                    request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
            }
            request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
