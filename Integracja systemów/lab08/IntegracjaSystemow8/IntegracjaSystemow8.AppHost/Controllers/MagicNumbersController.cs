using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace IntegracjaSystemow8.AppHost.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MagicNumbersController : Controller
    {
        private int[] numbers = new int[] { 2, 3, 5, 7, 11, 13 };
        [Authorize(Roles = "number", AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        [HttpGet]
        public IActionResult GetAll()
        {
            Random r = new Random();
            var magicNumber = numbers[r.Next(numbers.Length)];
            return Ok(magicNumber);
        }
    }
}