#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.timer 1
execute as @s[tag=!origin] at @s run function emissary:attacks/phase4/godrays/__generated__/block/666
execute as @s[tag=origin] run function emissary:attacks/phase4/godrays/__generated__/block/667
kill @s[scores={em.timer=30..}]