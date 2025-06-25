package com.example.weathertaskapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertaskapp.Adapter.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserListActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewUsers)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAddUser)


        userAdapter = UserAdapter { user ->
            val intent = Intent(this, WeatherActivity::class.java)
            intent.putExtra("city", user.city) // or use lastName/email/default city
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val user = userAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteUser(user)
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)


        // ViewModel setup
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[UserViewModel::class.java]

        // Observe data from ViewModel
        viewModel.userList.observe(this) { users ->
            userAdapter.submitList(users)
        }

        fabAdd.setOnClickListener {
            val intent = Intent(this, UserFormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers()
    }
}
