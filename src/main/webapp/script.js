// Week Cards Enhancement JavaScript// Week Cards Enhancement// Simple JavaScript for Week Cards Enhancement// Mobile Menu Toggle

document.addEventListener('DOMContentLoaded', function() {

    document.addEventListener('DOMContentLoaded', function() {

    // Week Cards Click Animation

    const weekCards = document.querySelectorAll('.week-card');    document.addEventListener('DOMContentLoaded', function() {const mobileMenu = document.getElementById('mobile-menu');

    

    weekCards.forEach(card => {    // Add click animation to week cards

        card.addEventListener('click', function() {

            const link = this.querySelector('.week-link');    const weekCards = document.querySelectorAll('.week-card');    const navMenu = document.getElementById('nav-menu');

            if (link) {

                this.style.transform = 'scale(0.95)';    

                setTimeout(() => {

                    window.location.href = link.href;    weekCards.forEach(card => {    // Add click animation to week cards

                }, 150);

            }        card.addEventListener('click', function() {

        });

                    const link = this.querySelector('.week-link');    const weekCards = document.querySelectorAll('.week-card');mobileMenu.addEventListener('click', () => {

        card.addEventListener('mouseenter', function() {

            this.style.boxShadow = '0 25px 50px rgba(0,0,0,0.2)';            if (link) {

        });

                        this.style.transform = 'scale(0.95)';        mobileMenu.classList.toggle('active');

        card.addEventListener('mouseleave', function() {

            this.style.boxShadow = '';                setTimeout(() => {

            this.style.transform = '';

        });                    window.location.href = link.href;    weekCards.forEach(card => {    navMenu.classList.toggle('active');

    });

                    }, 150);

    // Smooth Scroll for Anchor Links

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {            }        card.addEventListener('click', function() {});

        anchor.addEventListener('click', function (e) {

            e.preventDefault();        });

            const target = document.querySelector(this.getAttribute('href'));

            if (target) {                    // Find the link inside this card

                target.scrollIntoView({

                    behavior: 'smooth',        // Hover effects

                    block: 'start'

                });        card.addEventListener('mouseenter', function() {            const link = this.querySelector('.week-link');// Close mobile menu when clicking on a link

            }

        });            this.style.boxShadow = '0 25px 50px rgba(0,0,0,0.2)';

    });

            });            if (link) {document.querySelectorAll('.nav-link').forEach(link => {

    // Loading Animation for Assignment Links

    const assignmentLinks = document.querySelectorAll('a[href^="assignment/"]');        

    assignmentLinks.forEach(link => {

        link.addEventListener('click', function() {        card.addEventListener('mouseleave', function() {                // Add a brief scale animation before navigation    link.addEventListener('click', () => {

            const originalText = this.innerHTML;

            this.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang tải...';            this.style.boxShadow = '';

            this.style.pointerEvents = 'none';

                        this.style.transform = '';                this.style.transform = 'scale(0.95)';        mobileMenu.classList.remove('active');

            setTimeout(() => {

                this.innerHTML = originalText;        });

                this.style.pointerEvents = '';

            }, 3000);    });                setTimeout(() => {        navMenu.classList.remove('active');

        });

    });    

});
    // Smooth scroll                    window.location.href = link.href;    });

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {

        anchor.addEventListener('click', function (e) {                }, 150);});

            e.preventDefault();

            const target = document.querySelector(this.getAttribute('href'));            }

            if (target) {

                target.scrollIntoView({        });// Typing Animation

                    behavior: 'smooth',

                    block: 'start'        const typingElement = document.getElementById('typing');

                });

            }        // Add hover sound effect (optional)const typingTexts = [

        });

    });        card.addEventListener('mouseenter', function() {    'Web Developer',

    

    // Loading animation for assignment links            this.style.boxShadow = '0 25px 50px rgba(0,0,0,0.2)';    'UI/UX Designer', 

    const assignmentLinks = document.querySelectorAll('a[href^="assignment/"]');

    assignmentLinks.forEach(link => {        });    'Frontend Developer',

        link.addEventListener('click', function() {

            const originalText = this.innerHTML;            'Creative Coder',

            this.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang tải...';

            this.style.pointerEvents = 'none';        card.addEventListener('mouseleave', function() {    'Problem Solver'

            

            setTimeout(() => {            this.style.boxShadow = '';];

                this.innerHTML = originalText;

                this.style.pointerEvents = '';            this.style.transform = '';

            }, 3000);

        });        });let textIndex = 0;

    });

});    });let charIndex = 0;

    let isDeleting = false;

    // Smooth scroll for anchor links

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {function typeWriter() {

        anchor.addEventListener('click', function (e) {    const currentText = typingTexts[textIndex];

            e.preventDefault();    

            const target = document.querySelector(this.getAttribute('href'));    if (isDeleting) {

            if (target) {        typingElement.textContent = currentText.substring(0, charIndex - 1);

                target.scrollIntoView({        charIndex--;

                    behavior: 'smooth',    } else {

                    block: 'start'        typingElement.textContent = currentText.substring(0, charIndex + 1);

                });        charIndex++;

            }    }

        });    

    });    let typeSpeed = 100;

        

    // Add loading animation for external links    if (isDeleting) {

    const externalLinks = document.querySelectorAll('a[href^="assignment/"]');        typeSpeed = 50;

    externalLinks.forEach(link => {    }

        link.addEventListener('click', function() {    

            // Add loading state    if (!isDeleting && charIndex === currentText.length) {

            const originalText = this.innerHTML;        typeSpeed = 2000; // Pause at end

            this.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang tải...';        isDeleting = true;

            this.style.pointerEvents = 'none';    } else if (isDeleting && charIndex === 0) {

                    isDeleting = false;

            // Reset after a short delay (in case navigation fails)        textIndex = (textIndex + 1) % typingTexts.length;

            setTimeout(() => {        typeSpeed = 500; // Pause before starting new text

                this.innerHTML = originalText;    }

                this.style.pointerEvents = '';    

            }, 3000);    setTimeout(typeWriter, typeSpeed);

        });}

    });

});// Start typing animation when page loads

window.addEventListener('load', () => {

// Navbar toggle (if needed)    setTimeout(typeWriter, 1000);

function toggleNavbar() {});

    const navbar = document.querySelector('.navbar');

    if (navbar) {// Smooth scrolling for navigation links

        navbar.classList.toggle('active');document.querySelectorAll('a[href^="#"]').forEach(anchor => {

    }    anchor.addEventListener('click', function (e) {

}        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            const offsetTop = target.offsetTop - 80; // Account for fixed navbar
            window.scrollTo({
                top: offsetTop,
                behavior: 'smooth'
            });
        }
    });
});

// Navbar background on scroll
window.addEventListener('scroll', () => {
    const navbar = document.querySelector('.navbar');
    if (window.scrollY > 100) {
        navbar.style.background = 'rgba(255, 255, 255, 0.98)';
        navbar.style.boxShadow = '0 2px 20px rgba(0, 0, 0, 0.1)';
    } else {
        navbar.style.background = 'rgba(255, 255, 255, 0.95)';
        navbar.style.boxShadow = 'none';
    }
});

// Active navigation link highlighting
const sections = document.querySelectorAll('section');
const navLinks = document.querySelectorAll('.nav-link');

function highlightNavLink() {
    let current = '';
    sections.forEach(section => {
        const sectionTop = section.offsetTop - 100;
        if (window.scrollY >= sectionTop) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === `#${current}`) {
            link.classList.add('active');
        }
    });
}

window.addEventListener('scroll', highlightNavLink);

// Animate skill progress bars when in view
function animateSkillBars() {
    const skillBars = document.querySelectorAll('.skill-progress');
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const progressBar = entry.target;
                const width = progressBar.getAttribute('data-width');
                progressBar.style.width = width;
                observer.unobserve(progressBar);
            }
        });
    }, {
        threshold: 0.5
    });

    skillBars.forEach(bar => {
        observer.observe(bar);
    });
}

// Scroll animations
function scrollAnimations() {
    const animateElements = document.querySelectorAll('.about-card, .project-card, .blog-card, .assignment-card, .contact-item');
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    });

    animateElements.forEach(element => {
        element.style.opacity = '0';
        element.style.transform = 'translateY(30px)';
        element.style.transition = 'all 0.6s ease-out';
        observer.observe(element);
    });
}

// Contact form is now handled by Servlet - no JavaScript processing needed

// Notifications are now handled by Servlet redirects

// Project card hover effects
function initProjectCards() {
    const projectCards = document.querySelectorAll('.project-card');
    
    projectCards.forEach(card => {
        const image = card.querySelector('.project-image img');
        
        card.addEventListener('mouseenter', () => {
            if (image) {
                image.style.transform = 'scale(1.1)';
            }
        });
        
        card.addEventListener('mouseleave', () => {
            if (image) {
                image.style.transform = 'scale(1)';
            }
        });
    });
}

// Assignment cards are now managed by Servlet

// Parallax effect for home section
function initParallax() {
    const homeSection = document.querySelector('.home');
    
    window.addEventListener('scroll', () => {
        const scrolled = window.pageYOffset;
        const parallaxSpeed = 0.5;
        
        if (homeSection && scrolled < window.innerHeight) {
            homeSection.style.transform = `translateY(${scrolled * parallaxSpeed}px)`;
        }
    });
}

// Intersection Observer for fade-in animations
function initFadeInAnimations() {
    const fadeElements = document.querySelectorAll('.section-title, .section-subtitle');
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
                observer.unobserve(entry.target);
            }
        });
    }, {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    });

    fadeElements.forEach(element => {
        element.style.opacity = '0';
        element.style.transform = 'translateY(20px)';
        element.style.transition = 'all 0.8s ease-out';
        observer.observe(element);
    });
}

// Back to top button
function initBackToTop() {
    const backToTopBtn = document.createElement('button');
    backToTopBtn.innerHTML = '<i class="fas fa-chevron-up"></i>';
    backToTopBtn.className = 'back-to-top';
    backToTopBtn.style.cssText = `
        position: fixed;
        bottom: 30px;
        right: 30px;
        width: 50px;
        height: 50px;
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        border: none;
        border-radius: 50%;
        cursor: pointer;
        z-index: 1000;
        opacity: 0;
        visibility: hidden;
        transform: translateY(20px);
        transition: all 0.3s ease;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    `;
    
    document.body.appendChild(backToTopBtn);
    
    window.addEventListener('scroll', () => {
        if (window.scrollY > 500) {
            backToTopBtn.style.opacity = '1';
            backToTopBtn.style.visibility = 'visible';
            backToTopBtn.style.transform = 'translateY(0)';
        } else {
            backToTopBtn.style.opacity = '0';
            backToTopBtn.style.visibility = 'hidden';
            backToTopBtn.style.transform = 'translateY(20px)';
        }
    });
    
    backToTopBtn.addEventListener('click', () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });
    
    backToTopBtn.addEventListener('mouseenter', () => {
        backToTopBtn.style.transform = 'translateY(-3px) scale(1.1)';
    });
    
    backToTopBtn.addEventListener('mouseleave', () => {
        backToTopBtn.style.transform = 'translateY(0) scale(1)';
    });
}

// Preloader
function initPreloader() {
    const preloader = document.createElement('div');
    preloader.className = 'preloader';
    preloader.innerHTML = `
        <div class="preloader-content">
            <div class="preloader-spinner"></div>
            <p>Đang tải...</p>
        </div>
    `;
    preloader.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: white;
        z-index: 10000;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
    `;
    
    const content = preloader.querySelector('.preloader-content');
    content.style.cssText = `
        text-align: center;
        color: #667eea;
    `;
    
    const spinner = preloader.querySelector('.preloader-spinner');
    spinner.style.cssText = `
        width: 50px;
        height: 50px;
        border: 4px solid #f3f3f3;
        border-top: 4px solid #667eea;
        border-radius: 50%;
        animation: spin 1s linear infinite;
        margin-bottom: 1rem;
    `;
    
    // Add spinner animation
    const style = document.createElement('style');
    style.textContent = `
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    `;
    document.head.appendChild(style);
    
    document.body.appendChild(preloader);
    
    window.addEventListener('load', () => {
        setTimeout(() => {
            preloader.style.opacity = '0';
            preloader.style.transition = 'opacity 0.5s ease';
            setTimeout(() => {
                preloader.remove();
            }, 500);
        }, 1000);
    });
}

// Initialize all functions when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    initPreloader();
    animateSkillBars();
    scrollAnimations();
    initProjectCards();
    initParallax();
    initFadeInAnimations();
    initBackToTop();
});

// Performance optimization - debounce scroll events
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Apply debounce to scroll events
const debouncedScrollHandler = debounce(() => {
    highlightNavLink();
}, 10);

window.addEventListener('scroll', debouncedScrollHandler);