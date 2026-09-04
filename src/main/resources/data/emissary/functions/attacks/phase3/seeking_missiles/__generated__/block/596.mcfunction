#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s em.timer 1
execute at @s[tag=!end] run function emissary:attacks/phase3/seeking_missiles/slowcast
execute at @s[tag=!end] run function emissary:attacks/phase3/seeking_missiles/slowcast
execute as @s[tag=end] run function emissary:attacks/phase3/seeking_missiles/__generated__/block/597