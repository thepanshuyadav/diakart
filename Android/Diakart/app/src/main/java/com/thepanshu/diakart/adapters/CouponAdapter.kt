package com.thepanshu.diakart.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.CouponModel


class CouponAdapter(
        private val couponList: List<CouponModel>,
        private val context: Context,
        private val view: View
    ) : RecyclerView.Adapter<CouponAdapter.CouponAdapterViewHolder>() {
    inner class CouponAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val couponTitleTv: TextView = itemView.findViewById(R.id.coupon_title)
        private val couponDescTv: TextView = itemView.findViewById(R.id.coupon_desc)
        private val couponValueTv: TextView = itemView.findViewById(R.id.coupon_value)
        private val visitButton: Button = itemView.findViewById(R.id.visit_button)
        private val copyCoupon: ImageButton = itemView.findViewById(R.id.copy_coupon_button)

        fun setCouponTitle(title: String) {
            couponTitleTv.text = title
        }

        fun setCouponDesc(desc: String) {
            couponDescTv.text = desc
        }

        fun setCouponValue(value: String) {
            if(value != ""){
                Log.d("COUPON", couponList.toString())
                couponValueTv.text = value
            }else {
                copyCoupon.visibility = View.GONE
                couponValueTv.visibility = View.GONE
            }
        }

        fun setVisitButton(link: String) {
            visitButton.setOnClickListener {
                if(link != "") {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    context.startActivity(intent)
                }


            }
        }
        fun setCopyButton() {
            val myClipboard = getSystemService(context, ClipboardManager::class.java) as ClipboardManager
            copyCoupon.setOnClickListener {
                val clip = ClipData.newPlainText(context.getString(R.string.coupon), couponValueTv.text.toString())
                myClipboard.setPrimaryClip(clip)
                Snackbar.make(view, context.getString(R.string.text_copied), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponAdapterViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.coupon_item, parent, false)
        return CouponAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CouponAdapterViewHolder, position: Int) {
        holder.setCouponTitle(couponList[position].title)
        holder.setCouponDesc(couponList[position].desc)
        holder.setCouponValue(couponList[position].couponValue)
        holder.setVisitButton(couponList[position].link)
        holder.setCopyButton()

    }

    override fun getItemCount() = couponList.size

}