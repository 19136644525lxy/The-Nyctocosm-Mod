#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=!mobile] unless entity @e[tag=spell,tag=origin,tag=plane,distance=..0.3] run function spells:__generated__/block/160
execute as @s[tag=mobile] at @s run function spells:__generated__/block/161
execute as @s[tag=mobile] at @s unless entity @e[tag=spell,tag=origin,tag=plane,distance=..0.3] run function spells:__generated__/block/162