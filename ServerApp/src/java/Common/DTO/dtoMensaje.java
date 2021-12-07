package Common.DTO;
import Common.Enums.Status;
import java.util.List;

public class dtoMensaje 
{

    public dtoMensaje() {
        this.msg = "";
        this.tipo = "";
        this.colUsu =null;
        this.colEmp =null;
        this.colRol = null;
        this.colGridPer =null;
        this.colSup =null;
    }
    
    public dtoMensaje(String _msg, String _tipo) {
        this.msg = _msg;
        this.tipo = _tipo;
    }
    public dtoMensaje(String _msg, String _tipo,List<dtoUsuarioGrid> _colUsu,List<dtoEmpresa> _colEmp,List<dtoRol> _colRol,List<dtoGridPermiso> _colGridPer, List<dtoSupervisor> _colSup) {
        this.msg = _msg;
        this.tipo = _tipo;
        this.colUsu = _colUsu;
        this.colEmp = _colEmp;
        this.colRol = _colRol;
        this.colGridPer =_colGridPer;
        this.colSup = _colSup;
    }
    public void addMsgError(String msg) {
        this.msg += msg+"%";
        this.tipo = Status.danger.toString();        
    }
    
     public void addMsgOk(String msg) {
        this.msg += msg;
        this.tipo = Status.success.toString();        
    }
     
     public boolean isError() {
        if(this.msg != null && this.msg != "")
            return true;
        return false;
    }
    
    public String msg;
    public String tipo;
    public List<dtoUsuarioGrid> colUsu;
    public List<dtoEmpresa> colEmp;
    public List<dtoRol> colRol;
    public List<dtoGridPermiso> colGridPer;
    public List<dtoSupervisor> colSup;
}
