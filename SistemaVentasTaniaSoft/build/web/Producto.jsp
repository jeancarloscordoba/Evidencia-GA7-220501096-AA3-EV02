
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Sección | Producto</title>
    </head>
    <body>
        <div class="d-flex">
            <div class="card col-sm-4">
                <div class="card-body">
                    <form action="Controlador?menu=Producto" method="POST"> <!-- FORMULARIO PARA INGRESAR LOS DATOS DE UN NUEVO PRODUCTO -->
                        <div class="form-group">
                            <label>Nombre</label>
                            <input type="text" value="${producto.getNom()}" name="txtNombres" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Precio</label>
                            <input type="number" placeholder="$" value="${producto.getPrecio()}" name="txtPrecio" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Stock</label>
                            <input type="number" value="${producto.getStock()}" name="txtStock" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Descripcion</label>
                            <input type="text"  value="${producto.getDescripcion()}" name="txtDescripcion" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Estado</label>
                            <input type="text" value="${producto.getEstado()}" name="txtEstado" class="form-control">
                        </div> <!-- BOTONES PARA AGREGAR Y ACTUALIZAR UN PRODUCTO -->
                        <input type="submit" name="accion" value="Agregar" class="btn btn-info"> 
                        <input type="submit" name="accion" value="Actualizar" class="btn btn-success">
                    </form>
                </div>
            </div>
            <div class="col-sm-8">
                <table Class="table table-hover">
                    <thead>
                        <tr class="text-left">
                            <th>ID</th>
                            <th>NOMBRE</th>
                            <th>PRECIO</th>
                            <th>STOCK</th>
                            <th>DESCRIPCION</th>
                            <th>ESTADO</th>
                            <th>ACCIONES</th>
                        </tr>
                    </thead>
                    <tbody> 
                        <c:forEach var="pr" items="${productos}"> <!--El atributo productos se esta enviando desde el controlador.-->
                            <tr> <!-- ARROJAMIENTO DE LOS DATOS DE LOS PRODUCTOS AGREGADOS -->
                                <td>${pr.getId()}</td>
                                <td>${pr.getNom()}</td>
                                <td>${pr.getPrecio()}</td>
                                <td>${pr.getStock()}</td>
                                <td>${pr.getDescripcion()}</td>
                                <td>${pr.getEstado()}</td>
                                <td class="d-flex"> <!-- td PARA LOS BOTONES DE EDITAR Y ELIMINAR PRODUCTOS -->
                                    <a class="btn btn-warning" href="Controlador?menu=Producto&accion=Editar&id=${pr.getId()}">Editar</a>
                                    <a class="btn btn-danger" style="margin-left: 6px" href="Controlador?menu=Producto&accion=Delete&id=${pr.getId()}">Eliminar</a>
                                </td>
                            </tr>   
                        </c:forEach> 
                    </tbody>
                </table>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
