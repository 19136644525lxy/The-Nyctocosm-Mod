#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s run particle dust 1 0 0 3 ~ ~ ~ 0 0 0 0 3 force
execute as @s[tag=left,tag=1] at @e[tag=head,tag=serpent] run tp @s ^-2 ^2 ^1
execute as @s[tag=right,tag=1] at @e[tag=head,tag=serpent] run tp @s ^2 ^2 ^1
execute as @s[tag=left,tag=2] at @s run function serpent:__generated__/block/77
execute as @s[tag=left,tag=3] at @s run function serpent:__generated__/block/78
execute as @s[tag=left,tag=4] at @s run function serpent:__generated__/block/79
execute as @s[tag=left,tag=5] at @s run function serpent:__generated__/block/80
execute as @s[tag=right,tag=2] at @s run function serpent:__generated__/block/81
execute as @s[tag=right,tag=3] at @s run function serpent:__generated__/block/82
execute as @s[tag=right,tag=4] at @s run function serpent:__generated__/block/83
execute as @s[tag=right,tag=5] at @s run function serpent:__generated__/block/84