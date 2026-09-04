#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s rotated ~ 0 if block ^ ^-1 ^0.2 air if block ^ ^-2 ^0.2 air if block ^ ^-3 ^0.2 air if block ^ ^-4 ^0.2 air if block ^ ^-5 ^0.2 air run tag @s add stop
execute at @s rotated ~ 0 run tp @s[tag=!stop] ^ ^ ^0.2
execute at @s if block ~ ~-1 ~ air run tp @s ~ ~-1 ~
execute at @s unless block ~ ~ ~ air run tp @s ~ ~1 ~
tag @s remove stop