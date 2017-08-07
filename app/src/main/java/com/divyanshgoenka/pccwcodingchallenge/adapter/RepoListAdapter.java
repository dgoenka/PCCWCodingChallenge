package com.divyanshgoenka.pccwcodingchallenge.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.divyanshgoenka.pccwcodingchallenge.R;
import com.divyanshgoenka.pccwcodingchallenge.android.util.FormatUtils;
import com.divyanshgoenka.pccwcodingchallenge.model.Repo;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by divyanshgoenka on 07/08/17.
 */

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    private final List<Repo> repos;

    public RepoListAdapter(List<Repo> repos) {
        this.repos = repos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo repo = repos.get(position);
        if (!TextUtils.isEmpty(repo.getName()))
            holder.repoNameTextView.setText(repo.getName());
        if (!TextUtils.isEmpty(repo.getLanguage()))
            holder.repoLanguageTextView.setText(repo.getLanguage());
        if (repo.getStargazersCount() != null)
            holder.repoStarCountTextView.setText(repo.getStargazersCount().toString());
        if (repo.getForks() != null)
            holder.repoForkCountTextView.setText(repo.getForks().toString());
        if (!TextUtils.isEmpty(repo.getUpdatedAt()))
            try {
                holder.repoLastUpdatedTextView.setText(FormatUtils.parseForList(repo.getUpdatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    @Override
    public int getItemCount() {
        return repos == null ? 0 : repos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repo_name_text_view)
        TextView repoNameTextView;

        @BindView(R.id.repo_language_text_view)
        TextView repoLanguageTextView;

        @BindView(R.id.repo_star_count_text_view)
        TextView repoStarCountTextView;

        @BindView(R.id.repo_fork_count_text_view)
        TextView repoForkCountTextView;

        @BindView(R.id.repo_last_updated_text_view)
        TextView repoLastUpdatedTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
