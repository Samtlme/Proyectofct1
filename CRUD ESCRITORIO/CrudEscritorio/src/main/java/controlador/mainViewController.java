
package controlador;

import controlador.factory.DAOFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import modelo.dao.ProyectosDAO;
import modelo.dao.TareasDAO;
import modelo.dao.UsuariosDAO;
import modelo.vo.Proyectos;
import modelo.vo.Tareas;
import modelo.vo.Usuarios;
import vista.EditProjectView;
import vista.EditUserView;
import vista.EditWorkView;
import vista.MainView;

public class mainViewController {
    
    public static DAOFactory mySQLFactory;
    public static MainView ventana = new MainView();
    public static EditUserView ventanaEditUser;
    public static EditProjectView ventanaEditProject;
    public static EditWorkView ventanaEditWork;
    public static ProyectosDAO proyDAO;
    public static TareasDAO tareDAO;
    public static UsuariosDAO usuDAO;
    public static DefaultListModel listModelProyectos = new DefaultListModel();
    public static DefaultListModel listModelTareas = new DefaultListModel();
    public static DefaultListModel listModelUsuarios = new DefaultListModel();
    
    public static void iniciar(){
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getJListProyectos().setModel(listModelProyectos);
        ventana.getJListTareas().setModel(listModelTareas);
        ventana.getJListUsuarios().setModel(listModelUsuarios);

    }
    
    public static void cerrarFactory(){
    
        try {
            mySQLFactory.shutdown();
        } catch (Exception e) {
            System.out.println("Error cerrando Factory.");
        }
    }
    
    public static void iniciaFactory(){
        mySQLFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        
        //DAO's
        proyDAO = mySQLFactory.getProyectosDAO();
        tareDAO = mySQLFactory.getTareasDAO();
        usuDAO = mySQLFactory.getUsuariosDAO();
    }
    
    public static void ventanaInsertaTarea(){
        Tareas tarea;        
        ventanaEditWork = new EditWorkView();
        
        if(ventana.getJListTareas().getSelectedValue() == null){
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún elemento.");
            return;
        }
        
        if(ventana.getJListTareas().getSelectedValue() != null){
        
            tarea = getTareaById();
            ventanaEditWork.getTf_idTarea().setText(""+tarea.getId_tarea());
            ventanaEditWork.getTf_idProyectoAsociado().setText(""+tarea.getProyecto());
            ventanaEditWork.getTf_nombreTarea().setText(tarea.getNombre_tarea());
            ventanaEditWork.getTf_descripcionTarea().setText(tarea.getDescripcion());
            ventanaEditWork.getTf_estadoTarea().setText(tarea.getEstado());
            ventanaEditWork.getTf_puntosTarea().setText(""+tarea.getPuntos_tarea());
            ventanaEditWork.getTf_prioridadTarea().setText(tarea.getPrioridad_tarea());
        }
        ventanaEditWork.setModal(true);
        ventanaEditWork.setVisible(true);
    }
    
        public static void ventanaCrearTarea(){       
        ventanaEditWork = new EditWorkView();


        ventanaEditWork.getTf_idTarea().setText("");
        ventanaEditWork.getTf_idProyectoAsociado().setText("");
        ventanaEditWork.getTf_nombreTarea().setText("");
        ventanaEditWork.getTf_descripcionTarea().setText("");
        ventanaEditWork.getTf_estadoTarea().setText("");
        ventanaEditWork.getTf_puntosTarea().setText("");
        ventanaEditWork.getTf_prioridadTarea().setText("");
        ventanaEditWork.setModal(true);
        ventanaEditWork.setVisible(true);
    }
    
    public static void insertarTarea(){
        Connection conn = null;

        
        //noBlank 
        if(ventanaEditWork.getTf_idProyectoAsociado().getText().isBlank() ||
            ventanaEditWork.getTf_nombreTarea().getText().isBlank() ||
            ventanaEditWork.getTf_descripcionTarea().getText().isBlank()||
            ventanaEditWork.getTf_estadoTarea().getText().isBlank()||
            ventanaEditWork.getTf_puntosTarea().getText().isBlank()||
            ventanaEditWork.getTf_prioridadTarea().getText().isBlank()){

            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos.");
            return;
        }
        
        if(ventanaEditWork.getTf_nombreTarea().getText().length() >= 250){
            JOptionPane.showMessageDialog(null, "El nombre es demasiado largo.");
            return;
        }
        if(ventanaEditWork.getTf_descripcionTarea().getText().length() >= 500){
            JOptionPane.showMessageDialog(null, "La descripción es demasiado larga.");
            return;
        }
        if(!ventanaEditWork.getTf_estadoTarea().getText().toLowerCase().equals("pendiente")  &&
        !ventanaEditWork.getTf_estadoTarea().getText().toLowerCase().equals("completada")){
            JOptionPane.showMessageDialog(null, "Los unicos estados admitidos son 'pendiente' y 'completada'");
            return;
        }
                if(!ventanaEditWork.getTf_prioridadTarea().getText().toLowerCase().equals("s")  &&
                    !ventanaEditWork.getTf_prioridadTarea().getText().toLowerCase().equals("a") &&
                    !ventanaEditWork.getTf_prioridadTarea().getText().toLowerCase().equals("b")){
            JOptionPane.showMessageDialog(null, "Las unicas prioridades admitidas son 'S', 'A' o 'B'.");
            return;
        }
        
        
        
        try {
            conn = mySQLFactory.getConnection();

            if(ventanaEditWork.getTf_idTarea().getText().isBlank()){
            tareDAO.insetaTarea(conn,
                    Integer.valueOf(ventanaEditWork.getTf_idProyectoAsociado().getText()),
                    ventanaEditWork.getTf_nombreTarea().getText(),
                    ventanaEditWork.getTf_descripcionTarea().getText(),
                    ventanaEditWork.getTf_estadoTarea().getText(),
                    Integer.valueOf(ventanaEditWork.getTf_puntosTarea().getText()),
                    ventanaEditWork.getTf_prioridadTarea().getText());
            }else{
                 tareDAO.actualizarTarea(conn,new Tareas(
                    Integer.valueOf(ventanaEditWork.getTf_idTarea().getText()),
                    Integer.valueOf(ventanaEditWork.getTf_idProyectoAsociado().getText()),
                    ventanaEditWork.getTf_nombreTarea().getText(),
                    ventanaEditWork.getTf_descripcionTarea().getText(),
                    ventanaEditWork.getTf_estadoTarea().getText(),
                    Integer.valueOf(ventanaEditWork.getTf_puntosTarea().getText()),
                    ventanaEditWork.getTf_prioridadTarea().getText()));
            }
            
            
            conn.commit();
            JOptionPane.showMessageDialog(null,"Tarea insertada exitosamente.");
            
            
        } catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Los campos ID_proyecto_asociado y Puntos_tarea deben contener un valor numérico.");
        }catch (SQLException slqe){
            JOptionPane.showMessageDialog(null,"El proyecto no existe");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error insertando tarea");
        } finally {
            try {
                conn.commit();
            } catch (SQLException ex) {
                Logger.getLogger(mainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            mySQLFactory.releaseConnection(conn);
        }
        
    }
    
    public static void getAllTareas(){
        Connection conn = null;
        
        try {
            conn = mySQLFactory.getConnection();
            
            listModelTareas.clear();
            
            tareDAO.getAllTareas(conn, listModelTareas);
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error conectando con la base de datos.");
        } finally {

            mySQLFactory.releaseConnection(conn);
        }
    }
    
    public static Tareas getTareaById(){        //para cuando abre la ventana
        Connection conn = null;
        Tareas tarea = null;
        
        try {
            conn = mySQLFactory.getConnection();
            
            tarea = tareDAO.tareaById(conn, ventana.getJListTareas().getSelectedValue().getId_tarea());
            return tarea;

        } catch (Exception e) {
        } finally {
            mySQLFactory.releaseConnection(conn);
        }
         return null;
    }
    
    public static void actualizarTarea(){
        Connection conn = null;
        Tareas tarea = null;
        
            //noBlank 
            if(ventanaEditWork.getTf_idProyectoAsociado().getText().isBlank() ||
                ventanaEditWork.getTf_nombreTarea().getText().isBlank() ||
                ventanaEditWork.getTf_descripcionTarea().getText().isBlank()||
                ventanaEditWork.getTf_estadoTarea().getText().isBlank()||
                ventanaEditWork.getTf_puntosTarea().getText().isBlank()||
                ventanaEditWork.getTf_prioridadTarea().getText().isBlank()){
                
                JOptionPane.showMessageDialog(null, "No puede haber campos vacíos.");
                return;
            }
        
        tarea = new Tareas(Integer.valueOf(ventanaEditWork.getTf_idTarea().getText()),
                Integer.valueOf(ventanaEditWork.getTf_idProyectoAsociado().getText()),
                ventanaEditWork.getTf_nombreTarea().getText(),
                ventanaEditWork.getTf_descripcionTarea().getText(),
                ventanaEditWork.getTf_estadoTarea().getText(),
                Integer.valueOf(ventanaEditWork.getTf_puntosTarea().getText()),
                ventanaEditWork.getTf_prioridadTarea().getText());
        
        try {
            conn = mySQLFactory.getConnection();

            tareDAO.actualizarTarea(conn, tarea);
            conn.commit();
            JOptionPane.showMessageDialog(null, "Tarea actualizada exitosamente.");
        }catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Los campos ID_proyecto_asociado y Puntos_tarea deben contener un valor numérico.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error actualizando tarea");
        } finally {
            mySQLFactory.releaseConnection(conn);
            
        }
        
    }
    
    public static void borrarTarea(){
        Connection conn = null;
        
        
        try {
            conn = mySQLFactory.getConnection();
            int idTarea;
            if (ventana.getJListTareas().getSelectedValue().getId_tarea() != null) {
                idTarea = Integer.valueOf(ventana.getJListTareas().getSelectedValue().getId_tarea());
            } else {
                idTarea=-1;
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una tarea válida.");
            }
            tareDAO.borrarTarea(conn, idTarea);

            JOptionPane.showMessageDialog(null, "Tarea eliminada correctamente.");
            conn.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error eliminando tarea.");
        } finally {
            mySQLFactory.releaseConnection(conn);
        }
    }
    
    // PPROYECTOS 
    
    public static void ventanaInsertaProyecto(){
        Proyectos proyecto;        
        ventanaEditProject = new EditProjectView();
        
        if(ventana.getJListProyectos().getSelectedValue() == null){
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún elemento.");
            return;
        }
        
        if(ventana.getJListProyectos().getSelectedValue() != null){
        
            proyecto = getProyectoById();
            
            ventanaEditProject.getTf_idProyecto().setText(""+proyecto.getId_proyecto());
            ventanaEditProject.getTf_nombreProyecto().setText(""+proyecto.getNombre_proyecto());
            ventanaEditProject.getTf_descripcionProyecto().setText(""+proyecto.getDescripcion());
            ventanaEditProject.getTf_fechaInProyecto().setText(""+proyecto.getFecha_inicio());
            ventanaEditProject.getTf_fechaFinProyecto().setText(""+proyecto.getFecha_fin());
        }
        ventanaEditProject.setModal(true);
        ventanaEditProject.setVisible(true);
    }
    
        public static void ventanaCrearProyecto(){       
        ventanaEditProject = new EditProjectView();


            ventanaEditProject.getTf_idProyecto().setText("");
            ventanaEditProject.getTf_nombreProyecto().setText("");
            ventanaEditProject.getTf_descripcionProyecto().setText("");
            ventanaEditProject.getTf_fechaInProyecto().setText("");
            ventanaEditProject.getTf_fechaFinProyecto().setText("");
            ventanaEditProject.setModal(true);
            ventanaEditProject.setVisible(true);
    }
    
    public static void insertarProyecto(){
        Connection conn = null;

        
        //noBlank 
        if(ventanaEditProject.getTf_nombreProyecto().getText().isBlank() ||
            ventanaEditProject.getTf_descripcionProyecto().getText().isBlank() ||
            ventanaEditProject.getTf_fechaInProyecto().getText().isBlank()){

            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos, salvo la fecha final.");
            return;
        }
        
        if(ventanaEditProject.getTf_nombreProyecto().getText().length() >= 250){
            JOptionPane.showMessageDialog(null, "El nombre es demasiado largo.");
            return;
        }
        if(ventanaEditProject.getTf_descripcionProyecto().getText().length() >= 500){
            JOptionPane.showMessageDialog(null, "La descripción es demasiado larga.");
            return;
        }
       
        if(ventanaEditProject.getTf_fechaInProyecto().getText().length() > 10 ||
                ventanaEditProject.getTf_fechaFinProyecto().getText().length() > 10){ 
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. El formato debe ser AAAA-MM-DD");
            return;
        }
        
        try {
            conn = mySQLFactory.getConnection();

            if(ventanaEditProject.getTf_idProyecto().getText().isBlank()){
                
                if(ventanaEditProject.getTf_fechaFinProyecto().getText().isBlank()){
                    
                    proyDAO.insetaProyecto(conn,
                        ventanaEditProject.getTf_nombreProyecto().getText(),
                        ventanaEditProject.getTf_descripcionProyecto().getText(),
                        Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaInProyecto().getText())),
                        null);
                }else{
                proyDAO.insetaProyecto(conn,
                        ventanaEditProject.getTf_nombreProyecto().getText(),
                        ventanaEditProject.getTf_descripcionProyecto().getText(),
                        Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaInProyecto().getText())),
                        Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaFinProyecto().getText())));
                }
                
            }else{
                 if(ventanaEditProject.getTf_fechaFinProyecto().getText().isBlank()){
                    
                    proyDAO.actualizarProyecto(conn,
                            new Proyectos(
                        Integer.valueOf(ventanaEditProject.getTf_idProyecto().getText()),
                        ventanaEditProject.getTf_nombreProyecto().getText(),
                        ventanaEditProject.getTf_descripcionProyecto().getText(),
                        Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaInProyecto().getText())),
                        null));
                }else{
                    proyDAO.actualizarProyecto(conn,
                            new Proyectos(
                        Integer.valueOf(ventanaEditProject.getTf_idProyecto().getText()),
                        ventanaEditProject.getTf_nombreProyecto().getText(),
                        ventanaEditProject.getTf_descripcionProyecto().getText(),
                        Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaInProyecto().getText())),
                        Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaFinProyecto().getText()))));

                }
            }

            conn.commit();
            JOptionPane.showMessageDialog(null,"Proyecto insertado exitosamente.");
            
            
        } catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Id proyecto debe ser un numero");
        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(null,"Formato de fecha Incorrecto.El formato debe ser AAAA-MM-DD");
        }catch (SQLException slqe){
            JOptionPane.showMessageDialog(null,"Error en BD");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error insertando tarea");
        } finally {
            try {
                conn.commit();
            } catch (SQLException ex) {
                Logger.getLogger(mainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            mySQLFactory.releaseConnection(conn);
        }
        
    }
    
    public static void getAllProyectos(){
        Connection conn = null;
        
        try {
            conn = mySQLFactory.getConnection();
            
            listModelProyectos.clear();
            
            proyDAO.getAllProyectos(conn, listModelProyectos);
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error conectando con la base de datos.");
        } finally {

            mySQLFactory.releaseConnection(conn);
        }
    }
    
    public static Proyectos getProyectoById(){
        Connection conn = null;
        Proyectos proyecto = null;
        
        try {
            conn = mySQLFactory.getConnection();
            
            proyecto = proyDAO.proyectoById(conn,ventana.getJListProyectos().getSelectedValue().getId_proyecto());
            return proyecto;

        } catch (Exception e) {
        } finally {
            mySQLFactory.releaseConnection(conn);
        }
         return null;
    }
    
    public static void actualizarProyecto(){
        Connection conn = null;
        Proyectos proyecto = null;
        
            //noBlank 
        if(ventanaEditProject.getTf_nombreProyecto().getText().isBlank() ||
            ventanaEditProject.getTf_descripcionProyecto().getText().isBlank() ||
            ventanaEditProject.getTf_fechaInProyecto().getText().isBlank()){

            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos, salvo la fecha final.");
            return;
        }
        
        if(ventanaEditProject.getTf_nombreProyecto().getText().length() >= 250){
            JOptionPane.showMessageDialog(null, "El nombre es demasiado largo.");
            return;
        }
        if(ventanaEditProject.getTf_descripcionProyecto().getText().length() >= 500){
            JOptionPane.showMessageDialog(null, "La descripción es demasiado larga.");
            return;
        }
       
        if(ventanaEditProject.getTf_fechaInProyecto().getText().length() > 10 ||
                ventanaEditProject.getTf_fechaFinProyecto().getText().length() > 10){ 
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. El formato debe ser AAAA-MM-DD");
            return;
        }
       
        
        if(ventanaEditProject.getTf_fechaFinProyecto().getText().isBlank()){
            proyecto = new Proyectos(Integer.valueOf(ventanaEditProject.getTf_idProyecto().getText()),
            ventanaEditProject.getTf_nombreProyecto().getText(),
            ventanaEditProject.getTf_descripcionProyecto().getText(),
            Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaInProyecto().getText())),
            null);
        }else{
            proyecto = new Proyectos(Integer.valueOf(ventanaEditProject.getTf_idProyecto().getText()),
            ventanaEditProject.getTf_nombreProyecto().getText(),
            ventanaEditProject.getTf_descripcionProyecto().getText(),
            Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaInProyecto().getText())),
            Date.valueOf(LocalDate.parse(ventanaEditProject.getTf_fechaFinProyecto().getText())));
        }
        

        
        try {
            conn = mySQLFactory.getConnection();

            proyDAO.actualizarProyecto(conn, proyecto);
            conn.commit();
            JOptionPane.showMessageDialog(null, "Proyecto actualizado exitosamente.");
        }catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "El campo Id proyecto debe contener un valor numérico.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error actualizando proyecto");
        } finally {
            mySQLFactory.releaseConnection(conn);
            
        }
        
    }
    
    public static void borrarProyecto(){
        Connection conn = null;
        
        try {
            conn = mySQLFactory.getConnection();
            int idProyecto;
            if (ventana.getJListProyectos().getSelectedValue().getId_proyecto() != null) {
                idProyecto = Integer.valueOf(ventana.getJListProyectos().getSelectedValue().getId_proyecto());
            } else {
                idProyecto=-1;
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un proyecto válido.");
            }
            proyDAO.borrarProyecto(conn, idProyecto);

            JOptionPane.showMessageDialog(null, "proyecto eliminado correctamente.");
            conn.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error eliminando proyecto.");
        } finally {
            mySQLFactory.releaseConnection(conn);
        }
    }
    
    
    // Usuarios 
    
    public static void ventanaInsertaUsuario(){
        Usuarios usuario;        
        ventanaEditUser = new EditUserView();
        
        if(ventana.getJListUsuarios().getSelectedValue() == null){
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún elemento.");
            return;
        }
        
        if(ventana.getJListUsuarios().getSelectedValue() != null){
        
            usuario = getUsuarioById();
            
            ventanaEditUser.getTf_idUsuario().setText(""+usuario.getId_usuario());
            ventanaEditUser.getTf_nombreUsuario().setText(""+usuario.getNombre_usuario());
            ventanaEditUser.getTf_contrasenaUsuario().setText(""+usuario.getContrasena());
            ventanaEditUser.getTf_puntosUsuario().setText(""+usuario.getPuntos_totales());
            ventanaEditUser.getTf_rolUsuario().setText(""+usuario.getRol());
        }
        ventanaEditUser.setModal(true);
        ventanaEditUser.setVisible(true);
    }
    
        public static void ventanaCrearUsuario(){       
            ventanaEditUser = new EditUserView();

            ventanaEditUser.getTf_idUsuario().setText("");
            ventanaEditUser.getTf_nombreUsuario().setText("");
            ventanaEditUser.getTf_contrasenaUsuario().setText("");
            ventanaEditUser.getTf_puntosUsuario().setText("");
            ventanaEditUser.getTf_rolUsuario().setText("");
            ventanaEditUser.setModal(true);
            ventanaEditUser.setVisible(true);

    }
    
    public static void insertarUsuario(){
        Connection conn = null;

        
        //noBlank 
        if(ventanaEditUser.getTf_nombreUsuario().getText().isBlank() ||
            ventanaEditUser.getTf_contrasenaUsuario().getText().isBlank() ||
            ventanaEditUser.getTf_puntosUsuario().getText().isBlank() ||
            ventanaEditUser.getTf_rolUsuario().getText().isBlank()) {

            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos.");
            return;
        }
        
        if(ventanaEditUser.getTf_nombreUsuario().getText().length() >= 250){
            JOptionPane.showMessageDialog(null, "El nombre es demasiado largo.");
            return;
        }
        if(ventanaEditUser.getTf_contrasenaUsuario().getText().length() >= 250){
            JOptionPane.showMessageDialog(null, "La contraseña es demasiado larga.");
            return;
        }
        
        if(ventanaEditUser.getTf_puntosUsuario().getText().length() >= 5 ||
                ventanaEditUser.getTf_puntosUsuario().getText().contains("-") ||
                ventanaEditUser.getTf_puntosUsuario().getText().contains(".") ||
                ventanaEditUser.getTf_puntosUsuario().getText().contains(",")){
            JOptionPane.showMessageDialog(null, "El campo puntos debe ser un valor entero positivo de máximo 5 cifras.");
            return;
        }
       
        if(!ventanaEditUser.getTf_rolUsuario().getText().equals("1") &&
                !ventanaEditUser.getTf_rolUsuario().getText().equals("0")){
            JOptionPane.showMessageDialog(null, "El rol debe ser 0 o 1: 0 - normal, 1 - admin.");
            return;
        }
        
        try {
            conn = mySQLFactory.getConnection();

            if(ventanaEditUser.getTf_idUsuario().getText().isBlank()){

                usuDAO.insetaUsuario(conn,
                        ventanaEditUser.getTf_nombreUsuario().getText(),
                        ventanaEditUser.getTf_contrasenaUsuario().getText(),
                        Integer.valueOf(ventanaEditUser.getTf_puntosUsuario().getText()),
                        Integer.valueOf(ventanaEditUser.getTf_rolUsuario().getText()));
            }else{
                usuDAO.actualizarUsuario(conn, new Usuarios(
                        Integer.valueOf(ventanaEditUser.getTf_idUsuario().getText()),
                        ventanaEditUser.getTf_nombreUsuario().getText(),
                        ventanaEditUser.getTf_contrasenaUsuario().getText(),
                        Integer.valueOf(ventanaEditUser.getTf_puntosUsuario().getText()),
                        Integer.valueOf(ventanaEditUser.getTf_rolUsuario().getText())));

                
            }

            conn.commit();
            JOptionPane.showMessageDialog(null,"Usuario insertado exitosamente.");
            
            
        } catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Id Usuario, rol y puntos deben ser un numero entero");
        } catch (SQLException slqe){
            JOptionPane.showMessageDialog(null,"Error en BD");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error insertando tarea");
        } finally {
            try {
                conn.commit();
            } catch (SQLException ex) {
                Logger.getLogger(mainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            mySQLFactory.releaseConnection(conn);
        }
        
    }
    
    public static void getAllUsuarios(){
        Connection conn = null;
        
        try {
            conn = mySQLFactory.getConnection();
            
            listModelUsuarios.clear();
            
            usuDAO.getAllUsuarios(conn, listModelUsuarios);
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error conectando con la base de datos.");
        } finally {

            mySQLFactory.releaseConnection(conn);
        }
    }
    
    public static Usuarios getUsuarioById(){
        Connection conn = null;
        Usuarios usuario = null;
        
        try {
            conn = mySQLFactory.getConnection();
            
            usuario = usuDAO.usuarioById(conn,ventana.getJListUsuarios().getSelectedValue().getId_usuario());
            return usuario;

        } catch (Exception e) {
        } finally {
            mySQLFactory.releaseConnection(conn);
        }
         return null;
    }
    
    public static void actualizarUsuario(){
        Connection conn = null;
        Usuarios usuario = null;
        
        //noBlank 
        if(ventanaEditUser.getTf_nombreUsuario().getText().isBlank() ||
            ventanaEditUser.getTf_contrasenaUsuario().getText().isBlank() ||
            ventanaEditUser.getTf_puntosUsuario().getText().isBlank() ||
            ventanaEditUser.getTf_rolUsuario().getText().isBlank()) {

            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos.");
            return;
        }
        
        if(ventanaEditUser.getTf_nombreUsuario().getText().length() >= 250){
            JOptionPane.showMessageDialog(null, "El nombre es demasiado largo.");
            return;
        }
        if(ventanaEditUser.getTf_contrasenaUsuario().getText().length() >= 250){
            JOptionPane.showMessageDialog(null, "La contraseña es demasiado larga.");
            return;
        }
        
        if(ventanaEditUser.getTf_puntosUsuario().getText().length() >= 5 ||
                ventanaEditUser.getTf_puntosUsuario().getText().contains("-") ||
                ventanaEditUser.getTf_puntosUsuario().getText().contains(".") ||
                ventanaEditUser.getTf_puntosUsuario().getText().contains(",")){
            JOptionPane.showMessageDialog(null, "El campo puntos debe ser un valor entero positivo de máximo 5 cifras.");
            return;
        }
       
        if(!ventanaEditUser.getTf_rolUsuario().getText().equals("1") &&
                !ventanaEditUser.getTf_rolUsuario().getText().equals("0")){
            JOptionPane.showMessageDialog(null, "El rol debe ser 0 o 1: 0 - normal, 1 - admin.");
            return;
        }
        
        
        try {
            conn = mySQLFactory.getConnection();

            usuDAO.actualizarUsuario(conn, usuario);
            conn.commit();
            JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente.");
        }catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Id Usuario, rol y puntos deben ser un numero entero");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error actualizando usuario");
        } finally {
            mySQLFactory.releaseConnection(conn);
            
        }

        
    }
    
    public static void borrarUsuario(){
        Connection conn = null;
        
        try {
            conn = mySQLFactory.getConnection();
            int idUsuario;
            if (ventana.getJListUsuarios().getSelectedValue().getId_usuario()!= null) {
                idUsuario = Integer.valueOf(ventana.getJListUsuarios().getSelectedValue().getId_usuario());
            } else {
                idUsuario=-1;
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario válido.");
            }
            usuDAO.borrarUsuario(conn, idUsuario);

            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
            conn.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error eliminando usuario.");
        } finally {
            mySQLFactory.releaseConnection(conn);
        }
    }
    
    
}
