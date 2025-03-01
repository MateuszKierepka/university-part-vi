<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use App\Models\User;

class Comment extends Model
{
    public function user()
    {
        return $this->belongsTo(User::class);
    }

    use HasFactory;
}
