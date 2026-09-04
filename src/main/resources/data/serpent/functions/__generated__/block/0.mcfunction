#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add SerpentDeath timer 1
execute at @e[tag=segment,tag=serpent,limit=1,sort=random] run function effects:nightmare_vein/single_random
execute at @e[tag=serpent,tag=head] positioned ~ ~30 ~ as @e[tag=target,tag=serpent] run function serpent:__generated__/block/1
execute if score SerpentDeath timer matches 100.. run function serpent:__generated__/block/2