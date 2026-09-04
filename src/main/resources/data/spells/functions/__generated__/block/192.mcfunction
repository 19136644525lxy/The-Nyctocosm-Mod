#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove success
execute at @s[tag=!a.transposition] unless entity @e[tag=spell,tag=origin,tag=plane,distance=..2] run function spells:__generated__/block/193
execute at @s[tag=a.transposition] unless entity @e[tag=spell,tag=origin,tag=plane,distance=..2] run function spells:__generated__/block/196