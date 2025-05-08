using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using IntegracjaSystemow8.AppHost.Entities;

namespace IntegracjaSystemow8.AppHost.Model
{
    public class AuthenticationResponse
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string Token { get; set; }
        public AuthenticationResponse(User user, string token)
        {
            Id = user.Id;
            Username = user.Username;
            Token = token;
        }
    }
}
