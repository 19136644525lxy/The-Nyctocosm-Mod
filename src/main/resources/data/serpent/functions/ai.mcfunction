#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=currentfacing] at @s run function serpent:__generated__/block/200
execute as @s[tag=target] if score SerpentState value matches 2 at @s run function serpent:__generated__/block/201