package com.example.compose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R

class ExpandActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Replace with your own content
                    DepartmentList(departments = getSampleData())
                }
            }

    }
}

@Composable
fun DepartmentList(departments: List<Department>) {
    LazyColumn {
        items(departments.size) { index ->
            DepartmentItem(department = departments[index])
        }
    }
}

@Composable
fun DepartmentItem(department: Department) {
    var isExpanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min) // 或者指定一个固定的高度
                .clickable {
                    isExpanded = !isExpanded
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = department.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        if (isExpanded) {

            Column(  // 间距
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                department.members.forEach { member ->
                    MemberItem(member = member)
                }
            }
          }
            //点击部门item展示成员时报错
            // java.lang.IllegalStateException: Vertically scrollable component was measured with an infinity maximum height constraints, which is disallowed.
            // One of the common reasons is nesting layouts like LazyColumn and Column(Modifier.verticalScroll())
        /*    LazyColumn(
                modifier = Modifier.height(IntrinsicSize.Min) // 或者指定一个固定的高度
            ){
                items(department.members.size) {
                    MemberItem(member = department.members[it])
                }
            }*/

    }
}

@Composable
fun MemberItem(member: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp) // 或者指定一个固定的高度
            .padding(30.dp,0.dp,0.dp,0.dp)
    ) {
        Icon(
            painter = painterResource(id = R.mipmap.ic_choiceness),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = member, modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewDepartmentList() {
    DepartmentList(departments = getSampleData())
}

fun getSampleData(): List<Department> {
    return listOf(
        Department("部门A", listOf("成员1", "成员2", "成员3")),
        Department("部门B", listOf("成员4", "成员5")),
        Department("部门C", listOf("成员6", "成员7", "成员8")),
        Department("部门D", listOf("成员9", "成员10", "成员11")),
        Department("部门E", listOf("成员12", "成员13", "成员14","成员15", "成员16", "成员17")),
        Department("部门F", listOf("成员15", "成员16", "成员17","成员12", "成员13", "成员14","成员12", "成员13", "成员14")),
    )
}

data class Department(val name: String, val members: List<String>)

