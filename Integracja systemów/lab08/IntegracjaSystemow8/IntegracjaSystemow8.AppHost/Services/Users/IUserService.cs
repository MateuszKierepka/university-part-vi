using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using IntegracjaSystemow8.AppHost.Entities;
using IntegracjaSystemow8.AppHost.Model;

namespace IntegracjaSystemow8.AppHost.Services.Users
{
    public interface IUserService
    {
        AuthenticationResponse Authenticate(AuthenticationRequest request);
        IEnumerable<User> GetUsers();
        User GetByUsername(string username);
        User GetById(int id);

    }
}
