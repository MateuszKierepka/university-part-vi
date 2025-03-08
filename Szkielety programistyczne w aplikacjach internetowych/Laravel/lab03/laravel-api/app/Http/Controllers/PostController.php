<?php

namespace App\Http\Controllers;

use App\Models\Post;
use Illuminate\Http\Request;
use Illuminate\Routing\Controllers\HasMiddleware;
use Illuminate\Routing\Controllers\Middleware;
use Illuminate\Support\Facades\Gate;

class PostController extends Controller implements HasMiddleware
{
    /**
     * Display a listing of the resource.
     */
    public static function middleware()
    {
        return [
            new Middleware('auth:sanctum', except: ['index', 'show'])
        ];
    }
    public function index()
    {
        return Post::all();
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $fields = $request->validate([
                'title' => 'required|max:255',
                'body' => 'required',
        ]);
        // $post = Post::create($fields);
        $post = $request->user()->posts()->create($fields);
        return $post;
    }

    /**
     * Display the specified resource.
     */
    public function show(Post $post)
    {
        return $post;
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Post $post)
    {
        $response = Gate::inspect('modify', $post);
        if (!$response->allowed()) {
            return response()->json(['message' => $response->message()], 403);
        }

        $fields = $request->validate([
            'title' => 'required|max:255',
            'body' => 'required',
        ]);
        $post->update($fields);
        return $post;
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Post $post)
    {
        $response = Gate::inspect('modify', $post);
        if (!$response->allowed()) {
            return response()->json(['message' => $response->message()], 403);
        }

        $post->delete();
        return ['message' => 'The post was deleted!'];
    }
}
