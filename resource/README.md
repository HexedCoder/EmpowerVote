# EmpowerVote

## UserData.tsv

The `UserData.tsv` file contains important information about authorized users. The file includes the following columns:

1. **Password**: The password for the user.
3. **Level**: The user's access level (either `admin` or `user`).
2. **Voted**: A flag indicating whether the user has voted (1 for yes, 0 for no). Always 0 for admin.

## VoteStatus.tsv

The `VoteStatus.tsv` file contains information about the current voting status. The file includes the following columns:

1. **Name**: The name of the voter.
2. **PotentialPosition**: The position being sought by candidate.
3. **CurrentVotes**: The number of votes the voter has cast.