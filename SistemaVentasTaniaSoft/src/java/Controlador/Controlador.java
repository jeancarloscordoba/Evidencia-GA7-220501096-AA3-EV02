package Controlador;

import Modelo.Cliente;
import Modelo.ClienteDAO;
import Modelo.Empleado;
import Modelo.EmpleadoDAO;
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
    int ide;
    int idc;

    public Controlador() {
        PersistanceController.makeEntitiMF();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");

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
