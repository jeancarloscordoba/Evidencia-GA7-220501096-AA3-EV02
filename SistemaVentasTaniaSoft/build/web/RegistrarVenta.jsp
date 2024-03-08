
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Seccion | Ventas</title>
    </head>
    <body>
        <div class="d-flex">
            <div class="col-lg-5 parte1">
                <div class="card">
                    <form action="Controlador?menu=NuevaVenta" method="POST">
                        <div class="card-body">
                            <!-- DATOS DEL CLIENTE -->
                            <div class="form-group"> 
                                <label>Datos del Cliente</label>
                            </div>
                            <div class="form-group d-flex">
                                <div class="col-sm-6 d-flex">
                                    <input type="text" name="DNIcliente" value="${cl.getDni()}" class="form-control" placeholder="(DNI)">
                                    <button type="submit" name="accion" value="BuscarCliente" class="btn btn-outline-info">Buscar</button> <!-- Boton para buscar cliente por DNI -->
                                </div>
                                <div class="col-sm-6">
                                    <input type="text" name="nombrescliente" value="${cl.getNom()}" placeholder="Datos Cliente" class="form-control">
                                </div>
                            </div>
                                <!-- DATOS DEL PRODUCTO -->
                            <div class="form-group">
                                <label>Datos Producto</label>
                            </div>
                            <div class="form-group d-flex">
                                <div class="col-sm-6 d-flex">
                                    <input type="text" name="IDproducto" value="${producto.getId()}" class="form-control" placeholder="Producto (ID)">
                                    <button type="submit" name="accion" value="BuscarProducto" class="btn btn-outline-info">Buscar</button> <!-- Boton para buscar producto por ID de producto -->
                                </div>
                                <div class="col-sm-6">
                                    <input type="text" name="nomproducto" value="${producto.getNom()}" placeholder="Datos Producto" class="form-control">
                                </div>
                            </div>
                            <div class="form-group d-flex">
                                <div class="col-sm-6 d-flex"> 
                                    <input type="text" name="precio"  value="${producto.getPrecio()}" placeholder="$" class="form-control">
                                </div>
                                <div class="col-sm-3">
                                    <input type="number" value="1" name="cant"  placeholder="" class="form-control">
                                </div>
                                <div class="col-sm-3">
                                    <input type="text" name="stock" value="${producto.getStock()}" placeholder="Stock" class="form-control">
                                </div>
                            </div>
                                <!-- BOTON AGREGAR PRODUCTO AL REGISTRO DE PRODUCTOS PARA GENERAR LA NUEVA VENTA -->
                            <div class="form-group">
                                <div class="col-sm">
                                    <button type="submit" name="accion" value="AgregarProducto" class="btn btn-outline-primary">Agregar Producto</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-sm-7">
                <div class="card parte2">
                    <div class="card-body">
                        <!-- APARTADO DEL NUMERO DE SERIE -->
                        <div class="d-flex ml-auto col-sm-6">
                            <label class="text-right mt-2 col-sm-6 font-weight-bold">NRO. SERIE</label>
                            <input readonly="" type="text" name="numeroserie" value="${nserie}" class="form-control text-center font-weight-bold">
                        </div>
                        <br>
                        <table class="table table-hover table-responsive"> 
                            <thead>
                                <tr class="text-center">
                                    <th>N°</th>
                                    <th>ID</th>
                                    <th>PRODUCTO</th>
                                    <th>PRECIO</th>
                                    <th>CANTIDAD</th>
                                    <th>SUBTOTAL</th>
                                    <th class="text-left">ACCIONES</th>
                                </tr>
                            </thead>
                            <tbody> 
                                <c:forEach var="list" items="${lista}"> <!--El atributo lista se esta enviando desde el controlador.-->
                                    <tr class="text-center"> <!-- ARROJAMIENTO DE LOS DATOS DE LOS PRODUCTOS AGREGADOS -->
                                        <td>${list.getItem()}</td>
                                        <td>${list.getIdproducto()}</td>
                                        <td>${list.getDescripcionP()}</td>
                                        <td>${list.getPrecio()}</td>
                                        <td>${list.getCantidad()}</td>
                                        <td>${list.getSubtotal()}</td>
                                        <td class="d-flex"> <!-- td PARA LOS BOTONES DE EDITAR Y ELIMINAR PRODUCTOS AGREGADOS A LA NUEVA VENTA QUE SE REALIZARA -->
                                            <a href="Controlador?menu=NuevaVenta&accion=eliminarproducto&id=${list.getIdproducto()}" class="btn btn-danger" style="margin-left: 6px">Eliminar</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="card-footer">
                        <div class="row">
                            <div class="col-sm-6"> <!-- DIV PARA LOS BOTONES DE GENERAR VENTA Y CANCELAR -->
                                <a href="Controlador?menu=NuevaVenta&accion=GenerarVenta" class="btn btn-success">Generar Venta</a>
                                <a href="Controlador?menu=NuevaVenta&accion=Cancelar" class="btn btn-danger">Cancelar</a>
                            </div>
                            <div class="col-sm-6 ml-auto d-flex">
                                <label class="col-sm-6 text-right mt-2">Total a Pagar</label>
                                <input type="text" name="txtTotal"  value="${totalpagar}" placeholder="$/." class="form-control text-center font-weight-bold"> <!-- aca no dimos para realizarlo --> <!-- ERROR -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
