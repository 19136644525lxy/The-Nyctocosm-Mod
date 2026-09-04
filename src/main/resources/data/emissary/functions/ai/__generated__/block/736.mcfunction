#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=emissary,tag=emissarymodel,tag=!corkscrewing,tag=!grappletry,tag=!grapplegrabbed] run scoreboard players remove FlyTimer em.value 1
execute if score FlyTimer em.value matches ..0 run function emissary:ai/__generated__/block/737