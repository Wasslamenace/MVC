
package galerie.controller;
import galerie.dao.TableauRepository;
import galerie.entity.Tableau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import galerie.dao.GalerieRepository;
import galerie.entity.Galerie;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/tableau")

public class TableauController {

    @Autowired
    private TableauRepository dao;

    @GetMapping(path = "show")
    
    
    public String afficheTousLesTableaux(Model model) {
        model.addAttribute("tableaux", dao.findAll());
        return "afficheTableaux";
    }

    @GetMapping(path = "add")
    public String montreLeFormulairePourAjout(@ModelAttribute("tableau") Tableau tableau) {
        return "formulaireTableau";
    }

    @PostMapping(path = "save")
    public String ajouteLeTableauPuisMontreLaListe(Tableau tableau, RedirectAttributes redirectInfo) {
        String message;
        
        try {
            dao.save(tableau);
            message = "Le tableau '" + tableau.getTitre() + "' a été correctement enregistré";
        }
        catch (DataIntegrityViolationException e) {
            message = "Erreur : Le tableau '" + tableau.getTitre() + "' existe déjà";
        }
        
        redirectInfo.addFlashAttribute("message", message);
        
        return "redirect:show";
    }
    

    @GetMapping(path = "delete")
    
    public String supprimeUnTableauPuisMontreLaListe(@RequestParam("id") Tableau tableau, RedirectAttributes redirectInfo) {
        String message = "Le tableau '" + tableau.getTitre() + "' a bien été supprimé";
        
        try {
            dao.delete(tableau);
        } 
        catch (DataIntegrityViolationException e) {
            message = "Erreur : Impossible de supprimer le tableau '" + tableau.getTitre() + "', il faut d'abord supprimer ses expositions et ses transactions";
        }
        
        redirectInfo.addFlashAttribute("message", message);
        
        return "redirect:show";
    }
}


