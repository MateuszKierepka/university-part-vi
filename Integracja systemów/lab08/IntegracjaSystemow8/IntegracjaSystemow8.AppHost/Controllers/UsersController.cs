using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using IntegracjaSystemow8.AppHost.Model;
using IntegracjaSystemow8.AppHost.Services.Users;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication.JwtBearer;

namespace IntegracjaSystemow8.AppHost.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private IUserService userService;
        public UsersController(IUserService userService)
        {
            this.userService = userService;
        }

        [HttpPost("authenticate")]
        public IActionResult Authenticate(AuthenticationRequest request)
        {
            var response = userService.Authenticate(request);
            if (response == null)
                return BadRequest(new { message = "Username or password is incorrect" });
            return Ok(response);
        }

        [Authorize(Roles="admin", AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme )]
        [HttpGet]
        public IActionResult GetAll()
        {
            var users = userService.GetUsers();
            return Ok(users);
        }

        [Authorize(Roles = "user", AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        [HttpGet("count")]
        public IActionResult GetUsersCount()
        {
            var users = userService.GetUsers();
            return Ok(users.Count());
        }
    }
}
