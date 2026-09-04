#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.value 1
execute at @s[tag=!end] run function emissary:attacks/phase3/flame_slash/slowcast
execute at @s[tag=end] run function emissary:attacks/phase3/flame_slash/impact