package es.recha.furry.contollers;

import es.recha.furry.service.InviteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/invites")
public class InviteAdminController {

    private final InviteService inviteService;

    public InviteAdminController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    public record InviteCreateRequest(String email, Long createdByUserId, Integer daysValid) {}

    @PostMapping
    public InviteService.CreatedInvite create(@RequestBody InviteCreateRequest req) {
        int days = (req.daysValid() == null) ? 7 : req.daysValid();
        return inviteService.createInvite(req.email(), req.createdByUserId(), days);
    }
}
